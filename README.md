# WSO2 LDAP Latency Tester

1. Update binary/resources/test.properties file accordingly
   IMPORTANT:for the "MEMBERSHIP.VALUE" property you need to provide a place holder for username as "{user}"
   Ex: MEMBERSHIP.VALUE = uid={user},ou=Users,dc=wso2,dc=org
   (We have updated the wells-test.properties as reference using the user-mgt.xml in the ticket)

2. Update binary/resource/users.csv with test users

3. Replace binary/resources/security/client-truststore.jks with the one which you have in the setup

4. Navigate to binary folder from terminal and run using command "java -jar wso2-ldap-latency-tester-1.0.0.jar"
