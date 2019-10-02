package org.wso2.carbon.identity.ldap.tester;

import org.wso2.carbon.identity.ldap.tester.util.ConfigLoader;
import org.wso2.carbon.identity.ldap.tester.util.UserLoader;
import org.wso2.carbon.identity.ldap.tester.util.Utils;

import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WSO2LdapLatencyTester {

    private static Hashtable<String, String> environment = new Hashtable<>();

    public static void main(String[] args) {

        long totalInitialTime = System.currentTimeMillis();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                WSO2 POOLED LDAP LATENCY TESTER                     |");
        System.out.println("----------------------------------------------------------------------");
        
        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                          Config Properties                         |");
        System.out.println("----------------------------------------------------------------------");

        Utils.initConfigs(environment);

        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                          Search Properties                         |");
        System.out.println("----------------------------------------------------------------------");

        String membershipProperty = ConfigLoader.getInstance().getProperty("MEMBERSHIP.PROPERTY");
        System.out.println("Membership property: " + membershipProperty);
        String membershipValue = ConfigLoader.getInstance().getProperty("MEMBERSHIP.VALUE");
        System.out.println("Membership value: " + membershipValue);
        String groupNameListFilter = ConfigLoader.getInstance().getProperty("GROUP.NAME.LIST_FILTER");
        System.out.println("Group name list filter: " + groupNameListFilter);
        String roleNameAttribute = ConfigLoader.getInstance().getProperty("GROUP.NAME.ATTRIBUTE");
        System.out.println("Group name attribute: " + roleNameAttribute);
        String groupSearchBase = ConfigLoader.getInstance().getProperty("GROUP.SEARCH.BASE");
        System.out.println("Group search base: " + groupSearchBase);
        String searchFilter = "(&" + groupNameListFilter + "(" + membershipProperty + "=" + membershipValue + "))";
        System.out.println("Search filter: " + searchFilter);

        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                           Test Properties                          |");
        System.out.println("----------------------------------------------------------------------");

        int numberOfIterations = Integer.valueOf(ConfigLoader.getInstance().getProperty("NUMBER.OF.ITERATIONS"));
        System.out.println("Number of iterations: " + numberOfIterations);
        int numberOfThreads = Integer.valueOf(ConfigLoader.getInstance().getProperty("NUMBER.OF.THREADS"));
        System.out.println("Number of threads: " + numberOfThreads);
        List<String> users = UserLoader.getInstance().getUsers();
        System.out.println("Number of users: " + users.size());
        boolean showSearchResult = Boolean.valueOf(ConfigLoader.getInstance().getProperty("SHOW.SEARCH.RESULT"));
        System.out.println("Show search result: " + showSearchResult);

        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                             Test Output                            |");
        System.out.println("----------------------------------------------------------------------");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        int counter = 1;
        for (int i = 0; i < numberOfIterations; i++) {
            for (String user : users) {
                Runnable worker = new LDAPSearchThread(environment, roleNameAttribute, groupSearchBase,
                        searchFilter.replace("{user}", user), counter, showSearchResult);
                executor.execute(worker);
                counter++;
            }
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        long totalFinalTime = System.currentTimeMillis();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("|                         All tests completed                        |");
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Total time " + (totalFinalTime - totalInitialTime) + " ms");
    }

}
