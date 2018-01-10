package org.wso2.carbon.identity.ldap.tester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LDAPSearchThread implements Runnable {

    private Hashtable<String, String> environment;
    private String roleNameAttribute;
    private String groupSearchBase;
    private String searchFilter;
    private int counter;
    private boolean showSearchResult;

    public LDAPSearchThread(Hashtable<String, String> environment, String roleNameAttribute, String groupSearchBase,
                            String searchFilter, int counter, boolean showSearchResult) {

        this.environment = environment;
        this.roleNameAttribute = roleNameAttribute;
        this.groupSearchBase = groupSearchBase;
        this.searchFilter = searchFilter;
        this.counter = counter;
        this.showSearchResult = showSearchResult;
    }

    @Override
    public void run() {

        DirContext ctx = null;
        NamingEnumeration<SearchResult> results = null;
        try {
            long t1 = System.currentTimeMillis();
            ctx = new InitialDirContext(environment);
            long t2 = System.currentTimeMillis();
            System.out.println("Initialization time for " + counter + ": " + (t2 - t1) + " ms");

            SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            searchControls.setReturningAttributes(new String[]{roleNameAttribute});

            long t3 = System.currentTimeMillis();
            results = ctx.search(groupSearchBase, searchFilter, searchControls);
            long t4 = System.currentTimeMillis();
            System.out.println("Search time for " + counter + ": " + (t4 - t3) + " ms");

            StringBuilder roles = new StringBuilder();
            while (results.hasMoreElements()) {
                SearchResult sr = results.next();
                if (sr.getAttributes() != null) {
                    Attribute attr = sr.getAttributes().get(roleNameAttribute);
                    if (attr != null) {
                        for (Enumeration vals = attr.getAll(); vals.hasMoreElements(); ) {
                            roles.append((String) vals.nextElement());
                            roles.append(" ");
                        }
                    }
                }
            }
            if (showSearchResult) {
                System.out.println("Search output for " + counter + ": " + roles.toString());
            }
        } catch (NamingException e) {
            System.out.println("ERROR is occurred with HasMore");
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
