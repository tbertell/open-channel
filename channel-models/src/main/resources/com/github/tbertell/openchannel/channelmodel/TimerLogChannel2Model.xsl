<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:osgi="http://www.osgi.org/xmlns/blueprint/v1.0.0">
<xsl:template match="/">
<timerLogChannelModel>
	<timerPeriodInMillis>10000</timerPeriodInMillis>
	<message><xsl:value-of select="osgi:blueprint/osgi:bean[@id='helloBean']/osgi:property/@value"/></message>
</timerLogChannelModel>
</xsl:template>
</xsl:stylesheet>