package org.example.exceptions;

public class UserLibraryNotFoundException extends RuntimeException{
    public UserLibraryNotFoundException(){
        super("UserLibrary not found");
    }
}
