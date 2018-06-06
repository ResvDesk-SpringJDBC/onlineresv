/**
 * 
 */
package com.telappoint.onlineresv.exceptions;

/**
 * 
 * @author Murali
 * 
 * {@link BuilderException} is handled when there 
 * is an exception in building object from type F to type T
 *
 */
public class BuilderException extends Exception {

	private static final long serialVersionUID = -957354462224798456L;
	
	private String message;
	
	public BuilderException() {}
	
	public BuilderException(String message) {  
        this.message = message;
    }  
	
    @Override
	public String toString(){  
        return message;  
    } 

}
