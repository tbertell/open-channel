<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="useCache" select="stockQuoteChannelModel/useCache" /> 
	<xsl:variable name="cacheTTL" select="stockQuoteChannelModel/cacheTTL" />
	<xsl:variable name="responseTimeLimit" select="stockQuoteChannelModel/responseTimeLimit" />
	<xsl:variable name="serviceProvider" select="stockQuoteChannelModel/serviceProvider" />
	<xsl:template match="/">
<blueprint xmlns="http://www.osgi.org/xmlns/blueprint/v1.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:cm="http://aries.apache.org/blueprint/xmlns/blueprint-cm/v1.0.0"
	xmlns:jaxws="http://cxf.apache.org/blueprint/jaxws" xmlns:cxf="http://cxf.apache.org/blueprint/core"
	xmlns:camel="http://camel.apache.org/schema/blueprint" xmlns:camelcxf="http://camel.apache.org/schema/blueprint/cxf"
	xsi:schemaLocation="
             http://www.osgi.org/xmlns/blueprint/v1.0.0 http://www.osgi.org/xmlns/blueprint/v1.0.0/blueprint.xsd
             http://cxf.apache.org/blueprint/jaxws http://cxf.apache.org/schemas/blueprint/jaxws.xsd
             http://cxf.apache.org/blueprint/core http://cxf.apache.org/schemas/blueprint/core.xsd
             ">
	<bean id="stockQuotePrimaryWSClient"
		class="com.github.tbertell.openchannel.service.StockQuoteWSClient">
		<property name="url" value="http://www.webservicex.net/stockquote.asmx" />
		<property name="cacheTTL" value="{$cacheTTL}" />
		<property name="slow" value="true" />
		<property name="useCache" value="{$useCache}" />
		<property name="responseTimeLimit" value="{$responseTimeLimit}" />
	</bean>
	<bean id="stockQuoteSecondaryWSClient"
		class="com.github.tbertell.openchannel.service.StockQuoteWSClient">
		<property name="url" value="http://www.webservicex.net/stockquote.asmx" />
		<property name="cacheTTL" value="{$cacheTTL}" />
		<property name="slow" value="false" />
		<property name="useCache" value="{$useCache}" />
		<property name="responseTimeLimit" value="{$responseTimeLimit}" />
	</bean>
	<bean id="eventPublisher" class="com.github.tbertell.openchannel.service.EventPublisher">
	</bean>

	<reference id="connectionFactory" interface="javax.jms.ConnectionFactory" />
	<bean id="activemq" class="org.apache.activemq.camel.component.ActiveMQComponent">
		<property name="connectionFactory" ref="connectionFactory" />
	</bean>

	<camelContext xmlns="http://camel.apache.org/schema/blueprint">
		<route id="stockQuoteChannel">
			<from
				uri="cxfrs://http://localhost:9000?resourceClasses=com.github.tbertell.openchannel.service.StockQuoteResource" />
			<to uri="log:input" />
				<xsl:choose>
			  		<xsl:when test="$serviceProvider='PRIMARY'">
				    <to uri="bean:stockQuotePrimaryWSClient" />
				  </xsl:when>
				  <xsl:otherwise>
				   <to uri="bean:stockQuoteSecondaryWSClient" />
				  </xsl:otherwise>
				</xsl:choose>
			
			<wireTap uri="seda:event">
				<body><simple>${header.params}</simple></body>
			</wireTap>
			<to uri="log:output" />
		</route>
		
		<route id="eventChannel">
			<from uri="seda:event" />
			<to uri="log:input" />
			<to uri="activemq:queue:EVENT.QUEUE" />
		</route>
	</camelContext>
</blueprint>
	</xsl:template>
</xsl:stylesheet>