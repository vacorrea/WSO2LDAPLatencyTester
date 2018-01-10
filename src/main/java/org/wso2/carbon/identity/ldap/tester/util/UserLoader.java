package org.wso2.carbon.identity.ldap.tester.util;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserLoader {

    private List<String> users = new ArrayList<>();

    private UserLoader() {

        init();
    }

    private static class LazyHolder {

        private static final UserLoader INSTANCE = new UserLoader();
    }

    public static UserLoader getInstance() {

        return UserLoader.LazyHolder.INSTANCE;
    }

    private void init() {

        try (BufferedReader br = new BufferedReader(new FileReader("resources/users.csv"))) {
            String line;
            while (StringUtils.isNotBlank(line = br.readLine())) {
                users.add(line);
            }
        } catch (IOException e) {
            System.out.println("Can't find/read 'resources/users.csv' file.");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public List<String> getUsers() {

        return users;
    }

}
