package com.kodemon.rest.exception;

import org.springframework.dao.DataAccessException;

import javax.ws.rs.WebApplicationException;

/**
 * An exception representing error while saving/updating the db record
 *
 * @author <a href="xseleng@fi.muni.cz">Maros Seleng, 422624</a>
 */
public class RestDataAccessException extends WebApplicationException {
    public RestDataAccessException(DataAccessException ex) {
        super("An exception has been thrown while performing database operation.", ex, 409);
    }
}
