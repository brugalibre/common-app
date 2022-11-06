package com.brugalibre.common.security.auth.config;

/**
 * The {@link WebSecurityConfigHelper} helps for customizing different security settings such as https, ant matchers and so on
 */
public interface WebSecurityConfigHelper {

   /**
    * Returns a list with request-matchers for the given role
    *
    * @param role the role
    * @return a list with ant-matchers for the given role
    */
   String[] getRequestMatcherForRole(String role);

   /**
    * @return the url for processing the login
    */
   String getLoginProcessingUrl();
}
