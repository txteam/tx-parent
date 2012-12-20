package com.tx.core.util;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <虚拟文件处理类,基于vfs进行二次封装，针对常用的，文件写入，文件读取，文件拷贝进行封装>
 * 
 * see http://commons.apache.org/vfs/filesystems.html
 * 
 * Local Files
   URI Format
   [file://] absolute-path
   Where absolute-path is a valid absolute file name for the local platform. UNC names are supported under Windows.
   Examples
   file:///home/someuser/somedir
   file:///C:/Documents and Settings
   file://///somehost/someshare/afile.txt
   /home/someuser/somedir
   c:\program files\some dir
   c:/program files/some dir
 * 
 * Zip, Jar and Tar
   Provides read-only access to the contents of Zip, Jar and Tar files.
   URI Format
   zip:// arch-file-uri[! absolute-path]
   jar:// arch-file-uri[! absolute-path]
   tar:// arch-file-uri[! absolute-path]
   tgz:// arch-file-uri[! absolute-path]
   tbz2:// arch-file-uri[! absolute-path]
   Where arch-file-uri refers to a file of any supported type, including other zip files. Note: if you would like to use the ! as normal character it must be escaped using %21.
   tgz and tbz2 are convenience for tar:gz and tar:bz2.
   Examples
   jar:../lib/classes.jar!/META-INF/manifest.mf
   zip:http://somehost/downloads/somefile.zip
   jar:zip:outer.zip!/nested.jar!/somedir
   jar:zip:outer.zip!/nested.jar!/some%21dir
   tar:gz:http://anyhost/dir/mytar.tar.gz!/mytar.tar!/path/in/tar/README.txt
   tgz:file://anyhost/dir/mytar.tgz!/somepath/somefile
 * 
 * gzip and bzip2
   Provides read-only access to the contents of gzip and bzip2 files.
   URI Format
   gz:// compressed-file-uri
   bz2:// compressed-file-uri
   Where compressed-file-uri refers to a file of any supported type. There is no need to add a ! part to the uri if you read the content of the file you always will get the uncompressed version.
   Examples
   gz:/my/gz/file.gz
 * 
 * HTTP and HTTPS
   Provides access to files on an HTTP server.
   URI Format
   http://[ username[: password]@] hostname[: port][ absolute-path]
   https://[ username[: password]@] hostname[: port][ absolute-path]
   File System Options
   proxyHost The proxy host to connect through.
   proxyPort The proxy port to use.
   cookies An array of Cookies to add to the request.
   maxConnectionsPerHost The maximum number of connections allowed to a specific host and port. The default is 5.
   maxTotalConnections The maximum number of connections allowed to all hosts. The default is 50.
   Examples
   http://somehost:8080/downloads/somefile.jar
   http://myusername@somehost/index.html
 * 
 * WebDAV
   Provides access to the files on an FTP server.
   URI Format
   ftp://[ username[: password]@] hostname[: port][ absolute-path]
   Examples
   ftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz
 * 
 * FTPS
   Provides access to the files on an FTP server over SSL.
   URI Format
   ftps://[ username[: password]@] hostname[: port][ absolute-path]
   Examples
   ftps://myusername:mypassword@somehost/pub/downloads/somefile.tgz
 * 
 * SFTP
   Provides access to the files on an SFTP server (that is, an SSH or SCP server).
   URI Format
   sftp://[ username[: password]@] hostname[: port][ absolute-path]
   Examples
   sftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz
 * 
 * CIFS
   Provides access to the files on a CIFS server, such as a Samba server, or a Windows share.
   URI Format
   smb://[ username[: password]@] hostname[: port][ absolute-path]
   Examples
   smb://somehost/home
 * 
 * Temporary Files
   Provides access to a temporary file system, or scratchpad, that is deleted when Commons VFS shuts down. The temporary file system is backed by a local file system.
   URI Format
   tmp://[ absolute-path]
   Examples
   tmp://dir/somefile.txt
 * 
 * res
   This is not really a filesystem, it just tries to lookup a resource using javas ClassLoader.getResource() and creates a VFS url for further processing.
   URI Format
   res://[ path]
   Examples
   res:path/in/classpath/image.png
   might result in jar:file://my/path/to/images.jar!/path/in/classpath/image.png
 * 
 * ram
   A filesystem which stores all the data in memory. You can configure the max size and a predicate (FileSelector). The predicate will be used to check if it is allowed to add a given file.
   URI Format
   ram://[ path]
   Examples
   ram:///any/path/to/file.txt
 * 
 * mime
   This filesystem can read mails and its attachements like archives.
   If a part in the parsed mail has no name, a dummy name will be generated. The dummy name is: _body_part_X where X will be replaced by the part number.
   URI Format
   mime:// mime-file-uri[! absolute-path]
   Examples
   mime:file:///your/path/mail/anymail.mime!/
   mime:file:///your/path/mail/anymail.mime!/filename.pdf
   mime:file:///your/path/mail/anymail.mime!/_body_part_0
 * 
 * @author  PengQingyang
 * @version  [版本号, 2012-10-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class VFSUtils {
    private static Logger logger = LoggerFactory.getLogger(VFSUtils.class);
    
    private static FileSystemManager fsManager = null;
    
    static {
        try {
            fsManager = VFS.getManager();
        } catch (FileSystemException e) {
            logger.error("init vfs fileSystemManager fail.", e);
        }
    }
    
    /**
     *<判断对应文件是否存在>
     *<功能详细描述>
     * @param filePath
     * @return
     * @throws IOException [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static boolean exists(String filePath) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IOException("File '" + filePath + "' is empty.");
        }
        FileObject fileObj = null;
        try {
            fileObj = fsManager.resolveFile(filePath);
            return fileObj.exists();
        } catch (FileSystemException e) {
            throw new IOException("File '" + filePath + "' resolveFile fail.");
        } finally {
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
      *<读取文件，将其中内容以byte进行输出>
      *<功能详细描述>
      *
      * @param filePath
      * @return
      * @throws IOException [参数说明]
      * 
      * @return byte[] [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static byte[] readFileToByteArray(String filePath)
            throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IOException("File '" + filePath + "' is empty.");
        }
        FileObject fileObj = null;
        InputStream in = null;
        try {
            fileObj = fsManager.resolveFile(filePath);
            if (fileObj.exists()) {
                System.out.println(fileObj.getType().getName());
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("File '" + filePath
                            + "' exists but is a directory");
                } else {
                    in = fileObj.getContent().getInputStream();
                    return IOUtils.toByteArray(in);
                }
            } else {
                throw new FileNotFoundException("File '" + filePath
                        + "' does not exist");
            }
        } catch (FileSystemException e) {
            throw new IOException("File '" + filePath + "' resolveFile fail.");
        } finally {
            IOUtils.closeQuietly(in);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
      *<读取文件内容>
      *<功能详细描述>
      *
      * @param filePath
      * @param encoding
      * @return
      * @throws IOException [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String readFileToString(String filePath, String encoding)
            throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IOException("File '" + filePath + "' is empty.");
        }
        FileObject fileObj = null;
        InputStream in = null;
        try {
            fileObj = fsManager.resolveFile(filePath);
            if (fileObj.exists()) {
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("File '" + filePath
                            + "' exists but is a directory");
                } else {
                    in = fileObj.getContent().getInputStream();
                    return IOUtils.toString(in, encoding);
                }
            } else {
                throw new FileNotFoundException("File '" + filePath
                        + "' does not exist");
            }
        } catch (FileSystemException e) {
            throw new IOException("File '" + filePath + "' resolveFile fail.");
        } finally {
            IOUtils.closeQuietly(in);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
      * <读取文件内容>
      * <功能详细描述>
      * @param filePath
      * @return
      * @throws IOException [参数说明]
      * 
      * @return String [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static String readFileToString(String filePath) throws IOException {
        return readFileToString(filePath, null);
    }
    
    /**
      *<读取文件内容>
      *<功能详细描述>
      * @param filePath
      * @param encoding
      * @return
      * @throws IOException [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<String> readLines(String filePath, String encoding)
            throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IOException("File '" + filePath + "' is empty.");
        }
        FileObject fileObj = null;
        InputStream in = null;
        try {
            fileObj = fsManager.resolveFile(filePath);
            if (fileObj.exists()) {
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("File '" + filePath
                            + "' exists but is a directory");
                } else {
                    in = fileObj.getContent().getInputStream();
                    return IOUtils.readLines(in, encoding);
                }
            } else {
                throw new FileNotFoundException("File '" + filePath
                        + "' does not exist");
            }
        } catch (FileSystemException e) {
            throw new IOException("File '" + filePath + "' resolveFile fail.");
        } finally {
            IOUtils.closeQuietly(in);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
      *<读取文件内容>
      *<功能详细描述>
      * @param filePath
      * @return
      * @throws IOException [参数说明]
      * 
      * @return List<String> [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static List<String> readLines(String filePath) throws IOException {
        return readLines(filePath, null);
    }
    
    /**
      *<将内容写入文件中>
      *<功能详细描述>
      * @param filePath
      * @param data
      * @param encoding
      * @throws IOException [参数说明]
      * 
      * @return void [返回类型说明]
      * @exception throws [异常类型] [异常说明]
      * @see [类、类#方法、类#成员]
     */
    public static void writeStringToFile(String filePath, String data,
            String encoding) throws IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new IOException("File '" + filePath + "' is empty.");
        }
        FileObject fileObj = null;
        OutputStream out = null;
        
        try {
            fileObj = fsManager.resolveFile(filePath);
            
            if (!fileObj.exists()) {
                fileObj.createFile();
            } else {
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("Write fail. File '" + filePath
                            + "' exists but is a directory");
                }
            }
            
            if (!fileObj.isWriteable()) {
                throw new IOException("Write fail. File '" + filePath
                        + "' exists but isWriteable is false.");
            }
            
            out = fileObj.getContent().getOutputStream();
            IOUtils.write(data, out, encoding);
        } catch (FileSystemException e) {
            throw new IOException("File '" + filePath + "' resolveFile fail.");
        } finally {
            IOUtils.closeQuietly(out);
            if (fileObj != null) {
                fileObj.close();
            }
        }
        
    }
    
    /**
     *<将内容写入文件中>
     *<功能详细描述>
     * @param filePath
     * @param data
     * @param encoding
     * @throws IOException [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
    */
    public static void writeStringToFile(String filePath, String data)
            throws IOException {
        writeStringToFile(filePath, data, null);
    }
    
    public static void write(String filePath, CharSequence data, String encoding)
            throws IOException {
        
    }
    
    public static void main(String[] args) throws Exception {
        //write
        //FileUtils.writeStringToFile(file, data, encoding);
        //FileUtils.writeStringToFile(file, data);
        
        //FileUtils.write(file, data);
        //FileUtils.write(file, data, encoding);
        //FileUtils.writeByteArrayToFile(file, data);
        //FileUtils.writeLines(file, lines);
        //FileUtils.writeLines(file, lines, lineEnding);
        //FileUtils.writeLines(file, encoding, lines);
        //FileUtils.writeLines(file, encoding, lines, lineEnding);
        //
        
        //read
        //FileUtils.readFileToByteArray(file);
        //FileUtils.readFileToString(file);
        //FileUtils.readFileToString(file, encoding);
        //FileUtils.readLines(file);
        //FileUtils.readLines(file, encoding);
        
        //        System.out.println(fsManager.resolveFile("D:/ide").isReadable());
        //        System.out.println(fsManager.resolveFile("D:/ide").isWriteable());
        //        //检查是否有人读/写这个文件
        //        System.out.println(fsManager.resolveFile("D:/ide").isContentOpen());
        //        System.out.println(fsManager.resolveFile("D:/ide")
        //                .getContent()
        //                .isOpen());
        
        /// fsManager.resolveFile("D:/test/createfilefolder1/file1.txt").createFile();
        
        //System.out.println(fsManager.resolveFile("D:/ide").getType());
        
        //FileUtils.writeStringToFile(file, data, encoding)
        
        //        try
        //        {
        //            VFSUtils.readFileToByteArray("D:/ide");
        //        }
        //        catch (Exception e)
        //        {
        //            e.printStackTrace();
        //        }
        
        //        System.out.println(VFSUtils.readFileToString("D:/ide/CreatePluginsConfig.java",
        //                "utf-8"));
        //        
        //        System.out.println(VFSUtils.readFileToString("D:/ide/springsource/spring-roo-1.2.1.RELEASE/readme.txt",
        //                null));
        
        FileObject tt = fsManager.resolveFile("D:/test/createfilefolder1/file1.txt");
        tt.createFile();
        System.out.println(tt instanceof Closeable);
        
    }
}
