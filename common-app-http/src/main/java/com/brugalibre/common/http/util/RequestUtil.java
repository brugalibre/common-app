package com.brugalibre.common.http.util;

import okhttp3.Request;

public class RequestUtil {
   private RequestUtil() {
      //private
   }

   /**
    * Returns a String representation of the given {@link Request} but with hidden bearer-token
    *
    * @param request the {@link Request}
    * @return a String representation of the given {@link Request} but with hidden bearer-token
    */
   public static String toString(Request request) {
      if (request == null) {
         return "null";
      }
      return replaceBearerAuthorization(request.toString());
   }

   private static String replaceBearerAuthorization(String headersString) {
      String headerTokenReplaced = headersString.replaceAll("(Authorization:Bearer)( )(\\S+)", "Authorization:Bearer ###,");
      if (headerTokenReplaced.endsWith(",")) {
         headerTokenReplaced = headerTokenReplaced + "]}";
         headerTokenReplaced = headerTokenReplaced.replace(",]}", "]}");
      }
      return headerTokenReplaced;
   }
}
