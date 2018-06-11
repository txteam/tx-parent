/*
 * 描          述:  <描述>
 * 修  改   人:  Administrator
 * 修改时间:  2018年6月9日
 * <修改描述:>
 */
package com.tx.test.spring;

import java.io.IOException;

import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

/**
 * <功能简述>
 * <功能详细描述>
 * 
 * @author  Administrator
 * @version  [版本号, 2018年6月9日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CachingMetadataReaderFactoryTest {
    
    public static void main(String[] args) throws IOException {
        CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        
        System.out.println();
        System.out.println("com/tx/test/mybatis/model/TestDemo");
        MetadataReader mr = metadataReaderFactory
                .getMetadataReader("com/tx/test/mybatis/model/TestDemo");
        
        System.out.println(mr.toString());
        
        System.out.println(mr.getClassMetadata());
        System.out.println(mr.getClassMetadata().getClassName());
        System.out.println(mr.getClassMetadata().getEnclosingClassName());
        System.out.println(mr.getClassMetadata().getSuperClassName());
        System.out.println(mr.getClassMetadata().getInterfaceNames());
        System.out.println(mr.getClassMetadata().getMemberClassNames());
        
        //返回基础类是否代表一个具体类，
        System.out.println(mr.getClassMetadata().isConcrete());
        //确定基础类是否独立，即是否*是顶级类或嵌套类（静态内部类）*可以独立于封闭类构造。
        System.out.println(mr.getClassMetadata().isIndependent());
        System.out.println(mr.getClassMetadata().isAbstract());
        System.out.println(mr.getClassMetadata().isAnnotation());
        System.out.println(mr.getClassMetadata().isFinal());
        System.out.println(mr.getClassMetadata().isInterface());
        
        System.out.println(mr.getAnnotationMetadata());
        System.out.println(mr.getResource());
        
        System.out.println();
        System.out.println("com/tx/test/model/TestModel1");
        mr = metadataReaderFactory
                .getMetadataReader("com/tx/test/model/TestModel1");
        
        System.out.println(mr.toString());
        
        System.out.println(mr.getClassMetadata());
        System.out.println(mr.getClassMetadata().getClassName());
        System.out.println(mr.getClassMetadata().getEnclosingClassName());
        System.out.println(mr.getClassMetadata().getSuperClassName());
        System.out.println(mr.getClassMetadata().getInterfaceNames());
        System.out.println(mr.getClassMetadata().getMemberClassNames());
        
        //返回基础类是否代表一个具体类，
        System.out.println(mr.getClassMetadata().isConcrete());
        //确定基础类是否独立，即是否*是顶级类或嵌套类（静态内部类）*可以独立于封闭类构造。
        System.out.println(mr.getClassMetadata().isIndependent());
        System.out.println(mr.getClassMetadata().isAbstract());
        System.out.println(mr.getClassMetadata().isAnnotation());
        System.out.println(mr.getClassMetadata().isFinal());
        System.out.println(mr.getClassMetadata().isInterface());
        
        System.out.println();
        System.out.println("com/tx/test/model/TestAbstractModel");
        mr = metadataReaderFactory
                .getMetadataReader("com/tx/test/model/TestAbstractModel");
        
        System.out.println(mr.toString());
        
        System.out.println(mr.getClassMetadata());
        System.out.println(mr.getClassMetadata().getClassName());
        System.out.println(mr.getClassMetadata().getEnclosingClassName());
        System.out.println(mr.getClassMetadata().getSuperClassName());
        System.out.println(mr.getClassMetadata().getInterfaceNames());
        System.out.println(mr.getClassMetadata().getMemberClassNames());
        
        //返回基础类是否代表一个具体类，
        System.out.println(mr.getClassMetadata().isConcrete());
        //确定基础类是否独立，即是否*是顶级类或嵌套类（静态内部类）*可以独立于封闭类构造。
        System.out.println(mr.getClassMetadata().isIndependent());
        System.out.println(mr.getClassMetadata().isAbstract());
        System.out.println(mr.getClassMetadata().isAnnotation());
        System.out.println(mr.getClassMetadata().isFinal());
        System.out.println(mr.getClassMetadata().isInterface());
        
        System.out.println();
        System.out.println("com/tx/test/model/TestInterface");
        mr = metadataReaderFactory
                .getMetadataReader("com/tx/test/model/TestInterface");
        
        System.out.println(mr.toString());
        
        System.out.println(mr.getClassMetadata());
        System.out.println(mr.getClassMetadata().getClassName());
        System.out.println(mr.getClassMetadata().getEnclosingClassName());
        System.out.println(mr.getClassMetadata().getSuperClassName());
        System.out.println(mr.getClassMetadata().getInterfaceNames());
        System.out.println(mr.getClassMetadata().getMemberClassNames());
        
        //返回基础类是否代表一个具体类，
        System.out.println(mr.getClassMetadata().isConcrete());
        //确定基础类是否独立，即是否*是顶级类或嵌套类（静态内部类）*可以独立于封闭类构造。
        System.out.println(mr.getClassMetadata().isIndependent());
        System.out.println(mr.getClassMetadata().isAbstract());
        System.out.println(mr.getClassMetadata().isAnnotation());
        System.out.println(mr.getClassMetadata().isFinal());
        System.out.println(mr.getClassMetadata().isInterface());
    }
}
