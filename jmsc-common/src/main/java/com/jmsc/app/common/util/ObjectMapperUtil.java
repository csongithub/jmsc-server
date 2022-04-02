/**
 * 
 */
package com.jmsc.app.common.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author chandan
 *
 */
public class ObjectMapperUtil {

	private static ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	private ObjectMapperUtil() {
	}

	public static <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}
	
	
	/**
	 * Create a List<T> Type of the input type T
	 * 
	 * @param <T>
	 * @param dtoType
	 * @return
	 */
	public static <T> Type createListType(T dtoType) {
		return new TypeToken<List<T>>() {}.getType();
	}
	
	
    public static final <T> List<T> mapListType(T dtoType, List<? extends Object> sourceType)
    {
    	return  new ModelMapper().map(sourceType,createListType(dtoType));
    }
 
    
    
    
    public static <D> D object(String json, Class<D> outClass) {
    	if(json == null)
    		return null;
		try {
			return new ObjectMapper().readValue(json, outClass);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
    
    public static String json(Object object) {
    	if(object == null)
    		return null;
    	try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
    }

}
