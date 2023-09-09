package com.brugalibre.common.http.model.response;


public class CommonHttpResponse implements HttpResponse {

   private Exception exception;
   private boolean successful;

   /**
    * Creates a new {@link CommonHttpResponse} which is successful by default, if the given exception is <code>null</code>
    *
    * @param exception the value of the exception
    */
   public CommonHttpResponse(Exception exception) {
      this.exception = exception;
      this.successful = exception == null;
   }

   public CommonHttpResponse() {
   }

   @Override
   public void setIsSuccessful(boolean successful) {
      this.successful = successful;
   }

   public Exception getException() {
      return exception;
   }

   /**
    * Sets the given Exception. Also sets the value of <code>successful</code> according the existence of the exception
    * E.g. if the exception is not null, then <code>success</code> is set to <code>false</code>
    *
    * @param exception the value of the exception
    */
   public void setException(Exception exception) {
      this.exception = exception;
      if (exception != null) {
         this.successful = false;
      }
   }

   @Override
   public boolean isSuccessful() {
      return successful;
   }
}
