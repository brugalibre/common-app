package com.brugalibre.common.http.model.response;


public class CommonHttpResponse implements HttpResponse {

   private boolean successful;

   @Override
   public void setIsSuccessful(boolean successful) {
      this.successful = successful;
   }

   @Override
   public boolean isSuccessful() {
      return successful;
   }
}
