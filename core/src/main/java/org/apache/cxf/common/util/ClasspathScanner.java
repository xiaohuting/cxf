/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cxf.common.util;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ClasspathScanner {
    public static final String ALL_FILES = "**/*";
    public static final String ALL_CLASS_FILES = ALL_FILES + ".class";
    public static final String ALL_PACKAGES = "*";
    public static final String CLASSPATH_URL_SCHEME = "classpath:";
    
    static final ClasspathScanner HELPER;
    static {
        ClasspathScanner theHelper = null;
        try {
            theHelper = new SpringClasspathScanner();
        } catch (Throwable ex) {
            theHelper = new ClasspathScanner();
        }
        HELPER = theHelper;
    }
    
    // Default packages list to ignore during classpath scanning 
    static final String[] PACKAGES_TO_SKIP = {"org.apache.cxf"}; 

    
    protected ClasspathScanner() {
    }    

    /**
     * Scans list of base packages for all classes marked with specific annotations. 
     * @param basePackage base package 
     * @param annotations annotations to discover
     * @return all discovered classes grouped by annotations they belong too 
     * @throws IOException class metadata is not readable 
     * @throws ClassNotFoundException class not found
     */
    public static Map< Class< ? extends Annotation >, Collection< Class< ? > > > findClasses(
        String basePackage, Class< ? extends Annotation > ... annotations) 
        throws IOException, ClassNotFoundException {
        return findClasses(Collections.singletonList(basePackage), Arrays.asList(annotations));
    }
    
    /**
     * Scans list of base packages for all classes marked with specific annotations. 
     * @param basePackages list of base packages 
     * @param annotations annotations to discover
     * @return all discovered classes grouped by annotations they belong too 
     * @throws IOException class metadata is not readable 
     * @throws ClassNotFoundException class not found
     */
    public static Map< Class< ? extends Annotation >, Collection< Class< ? > > > findClasses(
        Collection< String > basePackages, Class< ? extends Annotation > ... annotations) 
        throws IOException, ClassNotFoundException {
        return findClasses(basePackages, Arrays.asList(annotations));
    }
    
    /**
     * Scans list of base packages for all classes marked with specific annotations. 
     * @param basePackages list of base packages 
     * @param annotations annotations to discover
     * @return all discovered classes grouped by annotations they belong too 
     * @throws IOException class metadata is not readable 
     * @throws ClassNotFoundException class not found
     */
    public static Map< Class< ? extends Annotation >, Collection< Class< ? > > > findClasses(
        Collection< String > basePackages, List<Class< ? extends Annotation > > annotations) 
        throws IOException, ClassNotFoundException {
        return HELPER.findClassesInternal(basePackages, annotations);
        
    }
    
    protected Map< Class< ? extends Annotation >, Collection< Class< ? > > > findClassesInternal(
        Collection< String > basePackages, List<Class< ? extends Annotation > > annotations) 
        throws IOException, ClassNotFoundException {
        return Collections.emptyMap();
    }
    
    /**
     * Scans list of base packages for all resources with the given extension. 
     * @param basePackage base package 
     * @param extension the extension matching resources needs to have
     * @return list of all discovered resource URLs 
     * @throws IOException resource is not accessible
     */
    public static List<URL> findResources(String basePackage, String extension) 
        throws IOException {
        return findResources(Collections.singletonList(basePackage), extension);
    }
    
    /**
     * Scans list of base packages for all resources with the given extension. 
     * @param basePackages list of base packages 
     * @param extension the extension matching resources needs to have
     * @return list of all discovered resource URLs 
     * @throws IOException resource is not accessible
     */
    public static List<URL> findResources(Collection<String> basePackages, String extension) 
        throws IOException {
        return HELPER.findResourcesInternal(basePackages, extension);
    }
    
    protected List<URL> findResourcesInternal(Collection<String> basePackages, String extension) 
        throws IOException {
        return Collections.emptyList();
    }
    
}
