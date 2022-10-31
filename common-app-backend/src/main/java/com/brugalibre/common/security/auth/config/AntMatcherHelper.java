package com.brugalibre.common.security.auth.config;

import org.springframework.stereotype.Service;

@Service
public interface AntMatcherHelper {

   /**
    * Returns a list with ant-matchers for the given role
    *
    * @param role the role
    * @return a list with ant-matchers for the given role
    */
   String[] getAntMatcherForRole(String role);

   /**
    * @return the url which is called as soon as the login was successful
    */
   String getDefaultSuccessUrl();
}
