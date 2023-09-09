package com.brugalibre.common.http.service.response;

import com.brugalibre.common.http.model.response.ResponseWrapper;
import com.brugalibre.common.http.service.HttpService;
import okhttp3.Response;

import java.io.IOException;

/**
 * The {@link ResponseReader} reads the json response the {@link HttpService} has received and maps
 * it into a {@link ResponseWrapper}
 *
 * @author Dominic
 */
public interface ResponseReader<T> {

   /**
    * Reads the given {@link ResponseReader} and maps it into a {@link ResponseWrapper}
    *
    * @param response the Response to read
    * @return the {@link ResponseWrapper} which contains the parsed result as well as some status value about the success
    * @throws IOException an {@link IOException} thrown by {@link okhttp3.OkHttp}
    */
   ResponseWrapper<T> readResponse(Response response) throws IOException;

   /**
    * Creates an error response which contains further information about the failed call
    *
    * @param e   the exception which occurred
    * @param url the url which was posted in order to get a result
    * @return an error response which contains further information about the failed call, wrapped in a {@link ResponseWrapper}
    */
   ResponseWrapper<T> createErrorResponse(Exception e, String url);
}
