package org.boon;


import org.boon.core.reflection.BeanUtils;
import org.boon.core.reflection.Invoker;
import org.boon.core.reflection.MapObjectConversion;
import org.boon.core.reflection.Reflection;
import org.boon.core.Function;
import org.boon.di.ProviderInfo;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.boon.Exceptions.requireNonNull;

public class Lists {


    public static <T> T fromList( List<Object> list, Class<T> clazz ) {
        return MapObjectConversion.fromList( list, clazz );
    }

    public static <V> List<V> list( Class<V> clazz ) {
        return new ArrayList<>();
    }

    public static <V> List<V> list( Iterable<V> iterable ) {
        List<V> list = new ArrayList<>();
        for ( V o : iterable ) {
            list.add( o );
        }
        return list;
    }


    public static List<?> toListOrSingletonList( Object item ) {
        if ( item == null ) {
            return new ArrayList<>();
        } else if ( item.getClass().isArray() ) {
            final int length = Array.getLength( item );
            List<Object> list = new ArrayList<>();
            for ( int index = 0; index < length; index++ ) {
                list.add( Array.get( item, index ) );
            }
            return list;
        } else if ( item instanceof Collection ) {
            return list( ( Collection ) item );
        } else if ( item instanceof Iterator ) {
            return list( ( Iterator ) item );
        } else if ( item instanceof Enumeration ) {
            return list( ( Enumeration ) item );
        } else if ( item instanceof Iterable ) {
            return list( ( Iterable ) item );
        } else {
            List<Object> list = new ArrayList<>();
            list.add( item );
            return list;
        }
    }


    public static List<?> toList( Object item ) {
        if ( item == null ) {
            return new ArrayList<>();
        } else if ( item.getClass().isArray() ) {
            final int length = Array.getLength( item );
            List<Object> list = new ArrayList<>();
            for ( int index = 0; index < length; index++ ) {
                list.add( Array.get( item, index ) );
            }
            return list;
        } else if ( item instanceof Collection ) {
            return list( ( Collection ) item );
        } else if ( item instanceof Iterator ) {
            return list( ( Iterator ) item );
        } else if ( item instanceof Enumeration ) {
            return list( ( Enumeration ) item );
        } else if ( item instanceof Iterable ) {
            return list( ( Iterable ) item );
        } else {
            //return MapObjectConversion.toList( item );
            List<Object> list = new ArrayList<>();
            list.add( item );
            return list;

        }
    }
    public static <V> List<V> list( Collection<V> collection ) {
        return new ArrayList<>( collection );
    }



    public static <V, WRAP> List<WRAP> wrap(Class<WRAP> wrapper, Collection<V> collection ) {
        List<WRAP> list = new ArrayList<>( collection.size () );

        for (V v : collection) {
            WRAP wrap = Reflection.newInstance ( wrapper, v );
            list.add ( wrap );
        }
        return list;
    }


    public static <V, WRAP> List<WRAP> wrap(Class<WRAP> wrapper, V[] collection ) {
        List<WRAP> list = new ArrayList<>( collection.length );

        for (V v : collection) {
            WRAP wrap = Reflection.newInstance ( wrapper, v );
            list.add ( wrap );
        }
        return list;
    }

    public static <V> List<V> list( Enumeration<V> enumeration ) {
        List<V> list = new ArrayList<>();
        while ( enumeration.hasMoreElements() ) {
            list.add( enumeration.nextElement() );
        }
        return list;
    }


    public static <V> Enumeration<V> enumeration( final List<V> list ) {
        final Iterator<V> iter = list.iterator();
        return new Enumeration<V>() {
            @Override
            public boolean hasMoreElements() {
                return iter.hasNext();
            }

            @Override
            public V nextElement() {
                return iter.next();
            }
        };

    }


    public static <V> List<V> list( Iterator<V> iterator ) {
        List<V> list = new ArrayList<>();
        while ( iterator.hasNext() ) {
            list.add( iterator.next() );
        }
        return list;
    }

    public static <V, N> List<N> list( Function<V, N> function, final V... array ) {
        if ( array == null ) {
            return new ArrayList<>();
        }
        List<N> list = new ArrayList<>( array.length );

        for ( V v : array ) {
            list.add( function.apply( v ) );
        }
        return list;
    }


    @SafeVarargs
    public static <V> List<V> list( final V... array ) {
        if ( array == null ) {
            return new ArrayList<>();
        }
        List<V> list = new ArrayList<>( array.length );
        Collections.addAll( list, array );
        return list;
    }

    @SafeVarargs
    public static <V> List<V> safeList( final V... array ) {
        return new CopyOnWriteArrayList<>( array );
    }

    @SafeVarargs
    public static <V> List<V> linkedList( final V... array ) {
        if ( array == null ) {
            return new ArrayList<>();
        }
        List<V> list = new LinkedList<>();
        Collections.addAll( list, array );
        return list;
    }


    public static <V> List<V> safeList( Collection<V> collection ) {
        return new CopyOnWriteArrayList<>( collection );
    }

    public static <V> List<V> linkedList( Collection<V> collection ) {
        return new LinkedList<>( collection );
    }

    /**
     * Universal methods
     */
    @Universal
    public static int len( List<?> list ) {
        return list.size();
    }


    @Universal
    public static int lengthOf( List<?> list ) {
        return len (list);
    }

    public static boolean isEmpty( List<?> list ) {
        return list == null || list.size() == 0;
    }

    @Universal
    public static <V> boolean in( V value, List<?> list ) {
        return list.contains( value );
    }

    @Universal
    public static <V> void add( List<V> list, V value ) {
        list.add( value );
    }


    @Universal
    public static <V> void add( List<V> list, V... values ) {
        for (V v : values) {
            list.add( v );
        }
    }

    @Universal
    public static <T> T atIndex( List<T> list, final int index ) {

        return idx(list, index);

    }

    @Universal
    public static <T> T idx( List<T> list, final int index ) {
        int i = calculateIndex( list, index );
        if ( i > list.size() - 1 ) {
            i = list.size() - 1;
        }
        return list.get( i );

    }

    public static <T> List idxList( List<T> list, final int index ) {
        return (List) idx(list, index);
    }


    public static <T> Map idxMap( List<T> list, final int index ) {
        return (Map) idx(list, index);
    }


    @Universal
    public static <V> void atIndex( List<V> list, int index, V v ) {
        idx (list, index, v);
    }

    @Universal
    public static <V> void idx( List<V> list, int index, V v ) {
        int i = calculateIndex( list, index );
        list.set( i, v );
    }


    @Universal
    public static <V> List<V> sliceOf( List<V> list, int startIndex, int endIndex ) {
        return slc(list, startIndex, endIndex);
    }

    @Universal
    public static <V> List<V> slc( List<V> list, int startIndex, int endIndex ) {
        int start = calculateIndex( list, startIndex );
        int end = calculateIndex( list, endIndex );
        return list.subList( start, end );
    }


    @Universal
    public static <V> List<V> sliceOf( List<V> list, int startIndex ) {
        return slc(list, startIndex);
    }

    @Universal
    public static <V> List<V> slc( List<V> list, int startIndex ) {
        return slc( list, startIndex, list.size() );
    }

    @Universal
    public static <V> List<V> endSliceOf( List<V> list, int endIndex ) {
        return slcEnd( list, endIndex );
    }


    @Universal
    public static <V> List<V> slcEnd( List<V> list, int endIndex ) {
        return slc( list, 0, endIndex );
    }


    @Universal
    public static <V> List<V> copy( List<V> list ) {
        if ( list instanceof LinkedList ) {
            return new LinkedList<>( list );
        } else if ( list instanceof CopyOnWriteArrayList ) {
            return new CopyOnWriteArrayList<>( list );
        } else {
            return new ArrayList<>( list );
        }
    }

    @Universal
    public static <V> List<V> copy( CopyOnWriteArrayList<V> list ) {
        return new CopyOnWriteArrayList<>( list );
    }

    @Universal
    public static <V> List<V> copy( ArrayList<V> list ) {
        return new ArrayList<>( list );
    }

    @Universal
    public static <V> List<V> copy( LinkedList<V> list ) {
        return new LinkedList<>( list );
    }


    @Universal
    public static <V> void insert( List<V> list, int index, V v ) {
        int i = calculateIndex( list, index );
        list.add( i, v );
    }


    /* End universal methods. */
    private static <T> int calculateIndex( List<T> list, int originalIndex ) {
        final int length = list.size();

        int index = originalIndex;

        /* Adjust for reading from the right as in
        -1 reads the 4th element if the length is 5
         */
        if ( index < 0 ) {
            index = ( length + index );
        }


        /* Bounds check
            if it is still less than 0, then they
            have an negative index that is greater than length
         */
        if ( index < 0 ) {
            index = 0;
        }
        if ( index > length ) {
            index = length;
        }
        return index;
    }


    public static <T> List<T> listFromProperty( Class<T> propertyType, String propertyPath, Collection<?> list ) {
        List<T> newList = new ArrayList<>( list.size() );

        for ( Object item : list ) {
            T newItem = ( T ) BeanUtils.idx( item, propertyPath );
            newList.add( newItem );
        }

        return newList;

    }


    public static <T> List<T> listFromProperty( Class<T> propertyType, String propertyPath, Iterable<?> list ) {
        List<T> newList = new ArrayList<>(  );

        for ( Object item : list ) {
            T newItem = ( T ) BeanUtils.idx( item, propertyPath );
            newList.add( newItem );
        }

        return newList;

    }
    public static List<Map<String, Object>> toListOfMaps( List<?> list ) {
        return MapObjectConversion.toListOfMaps( list );
    }

    public static void setProperty(List<?> list, String propertyName, Object value) {
        for (Object object : list) {
            BeanUtils.idx(object, propertyName, value);
        }
    }

    public static List<?> mapTo(Class<?> cls, String methodName, Object[] objects) {

        List list = new ArrayList(objects.length);
        for (Object o : objects) {
            list.add( Invoker.invoke(cls,methodName, o ));
        }
        return list;
    }



    public static List<?> mapTo(Class<?> cls, String methodName, Iterable<?> objects) {

        List list = new ArrayList();
        for (Object o : objects) {
            list.add( Invoker.invoke(cls,methodName, o ));
        }
        return list;
    }


    public static List<?> mapTo(Class<?> cls, String methodName, Collection<?> objects) {

        List list = new ArrayList(objects.size());
        for (Object o : objects) {
            list.add( Invoker.invoke(cls,methodName, o ));
        }
        return list;
    }
}
