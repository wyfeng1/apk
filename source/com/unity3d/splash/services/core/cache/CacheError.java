package com.unity3d.splash.services.core.cache;

public enum CacheError
{
  static
  {
    FILE_ALREADY_CACHING = new CacheError("FILE_ALREADY_CACHING", 2);
    NOT_CACHING = new CacheError("NOT_CACHING", 3);
    JSON_ERROR = new CacheError("JSON_ERROR", 4);
    NO_INTERNET = new CacheError("NO_INTERNET", 5);
    MALFORMED_URL = new CacheError("MALFORMED_URL", 6);
    NETWORK_ERROR = new CacheError("NETWORK_ERROR", 7);
    ILLEGAL_STATE = new CacheError("ILLEGAL_STATE", 8);
    INVALID_ARGUMENT = new CacheError("INVALID_ARGUMENT", 9);
    UNSUPPORTED_ENCODING = new CacheError("UNSUPPORTED_ENCODING", 10);
    FILE_STATE_WRONG = new CacheError("FILE_STATE_WRONG", 11);
    CACHE_DIRECTORY_NULL = new CacheError("CACHE_DIRECTORY_NULL", 12);
    CACHE_DIRECTORY_TYPE_NULL = new CacheError("CACHE_DIRECTORY_TYPE_NULL", 13);
    CACHE_DIRECTORY_EXISTS = new CacheError("CACHE_DIRECTORY_EXISTS", 14);
    CacheError localCacheError = new CacheError("CACHE_DIRECTORY_DOESNT_EXIST", 15);
    CACHE_DIRECTORY_DOESNT_EXIST = localCacheError;
    $VALUES = new CacheError[] { FILE_IO_ERROR, FILE_NOT_FOUND, FILE_ALREADY_CACHING, NOT_CACHING, JSON_ERROR, NO_INTERNET, MALFORMED_URL, NETWORK_ERROR, ILLEGAL_STATE, INVALID_ARGUMENT, UNSUPPORTED_ENCODING, FILE_STATE_WRONG, CACHE_DIRECTORY_NULL, CACHE_DIRECTORY_TYPE_NULL, CACHE_DIRECTORY_EXISTS, localCacheError };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.cache.CacheError
 * JD-Core Version:    0.6.0
 */