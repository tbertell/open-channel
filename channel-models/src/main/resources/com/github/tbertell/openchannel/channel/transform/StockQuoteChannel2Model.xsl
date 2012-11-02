<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:osgi="http://www.osgi.org/xmlns/blueprint/v1.0.0" xmlns:camel="http://camel.apache.org/schema/blueprint">
<xsl:template match="/">
<stockQuoteChannelModel>
    <useCache><xsl:value-of select="osgi:blueprint/osgi:bean[@id='stockQuoteCache']/osgi:property[@name='useCache']/@value"/></useCache>
    <cacheTTL><xsl:value-of select="osgi:blueprint/osgi:bean[@id='stockQuoteCache']/osgi:property[@name='cacheTTL']/@value"/></cacheTTL>
    <responseTimeLimit><xsl:value-of select="osgi:blueprint/osgi:bean[@id='stockQuotePrimaryWSClient']/osgi:property[@name='responseTimeLimit']/@value"/></responseTimeLimit>
    <serviceProvider>
    <xsl:choose>
  		<xsl:when test="osgi:blueprint/camel:camelContext/camel:route[@id='stockQuoteChannel']/camel:choice/camel:when/camel:to[position()=1]/@uri='bean:stockQuotePrimaryWSClient'">
	    	PRIMARY
	  	</xsl:when>
	  	<xsl:otherwise>
	   		SECONDARY
	  	</xsl:otherwise>
	</xsl:choose>
    </serviceProvider>
</stockQuoteChannelModel>
</xsl:template>
</xsl:stylesheet>