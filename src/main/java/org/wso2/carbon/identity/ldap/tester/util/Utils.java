package org.wso2.carbon.identity.ldap.tester.util;

import org.apache.commons.lang.StringUtils;

import java.util.Hashtable;
import javax.naming.Context;

public class Utils {

    public static void initConfigs(Hashtable<String, String> environment) {

        String trustStoreLocation = ConfigLoader.getInstance().getProperty("TRUST.STORE.LOCATION");
        if (StringUtils.isNotBlank(trustStoreLocation)) {
            System.setProperty("javax.net.ssl.trustStore", trustStoreLocation);
            System.out.println("Trust store location: " + trustStoreLocation);
        } else {
            System.setProperty("javax.net.ssl.trustStore", "resources/security/client-truststore.jks");
        }

        String trustStorePassword = ConfigLoader.getInstance().getProperty("TRUST.STORE.PASSWORD");
        if (StringUtils.isNotBlank(trustStorePassword)) {
            System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
            System.out.println("Trust store password: " + trustStorePassword);
        } else {
            System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        }
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        environment.put(Context.SECURITY_AUTHENTICATION, "simple");
        environment.put(Context.REFERRAL, "follow");

        String connectionUrl = ConfigLoader.getInstance().getProperty("CONNECTION.URL");
        if (StringUtils.isNotBlank(connectionUrl)) {
            environment.put(Context.PROVIDER_URL, connectionUrl);
            System.out.println("Connection URL: " + connectionUrl);
        } else {
            environment.put(Context.PROVIDER_URL, "ldap://localhost:10389");
        }

        String connectionUsername = ConfigLoader.getInstance().getProperty("CONNECTION.USERNAME");
        if (StringUtils.isNotBlank(connectionUsername)) {
            environment.put(Context.SECURITY_PRINCIPAL, connectionUsername);
            System.out.println("Connection username: " + connectionUsername);
        } else {
            environment.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        }

        String connectionPassword = ConfigLoader.getInstance().getProperty("CONNECTION.PASSWORD");
        if (StringUtils.isNotBlank(connectionPassword)) {
            environment.put(Context.SECURITY_CREDENTIALS, connectionPassword);
            System.out.println("Connection password: " + connectionPassword);
        } else {
            environment.put(Context.SECURITY_CREDENTIALS, "admin");
        }
    }

}
