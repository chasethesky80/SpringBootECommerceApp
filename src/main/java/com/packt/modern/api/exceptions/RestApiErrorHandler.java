package com.packt.modern.api.exceptions;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class RestApiErrorHandler {

  private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex){
    return getErrorResponse(request, ErrorCode.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                                        HttpMediaTypeNotSupportedException ex){
    log.info("HttpMediaTypeNotSupportedException :: request.getMethod(): " + request.getMethod());
    return getErrorResponse(request, ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpMessageNotWritableException.class)
  public ResponseEntity<Error> handleHttpMessageNotWritableException(HttpServletRequest request,
                                                                     HttpMessageNotWritableException ex,
                                                                     Locale locale) {
    log.info("HttpMessageNotWritableException :: request.getMethod(): " + request.getMethod());
    ex.printStackTrace(); // TODO: Should be kept only for development
    return getErrorResponse(request, ErrorCode.HTTP_MESSAGE_NOT_WRITABLE, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<Error> handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
                                                                         HttpMediaTypeNotAcceptableException ex,
                                                                         Locale locale) {
    ex.printStackTrace(); // TODO: Should be kept only for development
    log.info("HttpMediaTypeNotAcceptableException :: request.getMethod(): " + request.getMethod());
    return getErrorResponse(request, ErrorCode.HTTP_MEDIA_TYPE_NOT_ACCEPTABLE, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpServletRequest request,
                                                                     HttpMessageNotReadableException ex,
                                                                     Locale locale) {
    log.info("HttpMessageNotReadableException :: request.getMethod(): " + request.getMethod());
    ex.printStackTrace(); // TODO: Should be kept only for development
    return getErrorResponse(request, ErrorCode.HTTP_MESSAGE_NOT_READABLE, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(JsonParseException.class)
  public ResponseEntity<Error> handleJsonParseException(HttpServletRequest request,
                                                        JsonParseException ex,
                                                        Locale locale) {
    log.info("JsonParseException :: request.getMethod(): " + request.getMethod());
    ex.printStackTrace(); // TODO: Should be kept only for development
    return getErrorResponse(request, ErrorCode.JSON_PARSE_ERROR, HttpStatus.NOT_ACCEPTABLE);
  }

  /**
   * Generates error response from request, errorCode and status
   * @param request
   * @param jsonParseError
   * @param httpStatus
   * @return
   */
  private static ResponseEntity<Error> getErrorResponse(final HttpServletRequest request,
                                                        final ErrorCode jsonParseError,
                                                        final HttpStatus httpStatus) {
    final Error error = ErrorUtils
            .createError(jsonParseError.getErrMsgKey(),
                    jsonParseError.getErrCode(),
                    httpStatus.value());
    error.setUrl(request.getRequestURL().toString());
    error.setReqMethod(request.getMethod());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
