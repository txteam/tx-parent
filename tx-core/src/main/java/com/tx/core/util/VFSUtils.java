package com.tx.core.util;

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
import org.apache.commons.vfs2.FileTypeSelector;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <虚拟文件处理类,基于vfs进行二次封装，针对常用的，文件写入，文件读取，文件拷贝进行封装>
 * 
 * see http://commons.apache.org/vfs/filesystems.html
 * 
 * Local Files URI Format [file://] absolute-path Where absolute-path is a valid
 * absolute file name for the local platform. UNC names are supported under
 * Windows. Examples file:///home/someuser/somedir file:///C:/Documents and
 * Settings file://///somehost/someshare/afile.txt /home/someuser/somedir
 * c:\program files\some dir c:/program files/some dir
 * 
 * Zip, Jar and Tar Provides read-only access to the contents of Zip, Jar and
 * Tar files. URI Format zip:// arch-file-uri[! absolute-path] jar://
 * arch-file-uri[! absolute-path] tar:// arch-file-uri[! absolute-path] tgz://
 * arch-file-uri[! absolute-path] tbz2:// arch-file-uri[! absolute-path] Where
 * arch-file-uri refers to a file of any supported type, including other zip
 * files. Note: if you would like to use the ! as normal character it must be
 * escaped using %21. tgz and tbz2 are convenience for tar:gz and tar:bz2.
 * Examples jar:../lib/classes.jar!/META-INF/manifest.mf
 * zip:http://somehost/downloads/somefile.zip
 * jar:zip:outer.zip!/nested.jar!/somedir
 * jar:zip:outer.zip!/nested.jar!/some%21dir
 * tar:gz:http://anyhost/dir/mytar.tar.gz!/mytar.tar!/path/in/tar/README.txt
 * tgz:file://anyhost/dir/mytar.tgz!/somepath/somefile
 * 
 * gzip and bzip2 Provides read-only access to the contents of gzip and bzip2
 * files. URI Format gz:// compressed-file-uri bz2:// compressed-file-uri Where
 * compressed-file-uri refers to a file of any supported type. There is no need
 * to add a ! part to the uri if you read the content of the file you always
 * will get the uncompressed version. Examples gz:/my/gz/file.gz
 * 
 * HTTP and HTTPS Provides access to files on an HTTP server. URI Format
 * http://[ username[: password]@] hostname[: port][ absolute-path] https://[
 * username[: password]@] hostname[: port][ absolute-path] File System Options
 * proxyHost The proxy host to connect through. proxyPort The proxy port to use.
 * cookies An array of Cookies to add to the request. maxConnectionsPerHost The
 * maximum number of connections allowed to a specific host and port. The
 * default is 5. maxTotalConnections The maximum number of connections allowed
 * to all hosts. The default is 50. Examples
 * http://somehost:8080/downloads/somefile.jar
 * http://myusername@somehost/index.html
 * 
 * WebDAV Provides access to the files on an FTP server. URI Format ftp://[
 * username[: password]@] hostname[: port][ absolute-path] Examples
 * ftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz
 * 
 * FTPS Provides access to the files on an FTP server over SSL. URI Format
 * ftps://[ username[: password]@] hostname[: port][ absolute-path] Examples
 * ftps://myusername:mypassword@somehost/pub/downloads/somefile.tgz
 * 
 * SFTP Provides access to the files on an SFTP server (that is, an SSH or SCP
 * server). URI Format sftp://[ username[: password]@] hostname[: port][
 * absolute-path] Examples
 * sftp://myusername:mypassword@somehost/pub/downloads/somefile.tgz
 * 
 * CIFS Provides access to the files on a CIFS server, such as a Samba server,
 * or a Windows share. URI Format smb://[ username[: password]@] hostname[:
 * port][ absolute-path] Examples smb://somehost/home
 * 
 * Temporary Files Provides access to a temporary file system, or scratchpad,
 * that is deleted when Commons VFS shuts down. The temporary file system is
 * backed by a local file system. URI Format tmp://[ absolute-path] Examples
 * tmp://dir/somefile.txt
 * 
 * res This is not really a filesystem, it just tries to lookup a resource using
 * javas ClassLoader.getResource() and creates a VFS url for further processing.
 * URI Format res://[ path] Examples res:path/in/classpath/image.png might
 * result in jar:file://my/path/to/images.jar!/path/in/classpath/image.png
 * 
 * ram A filesystem which stores all the data in memory. You can configure the
 * max size and a predicate (FileSelector). The predicate will be used to check
 * if it is allowed to add a given file. URI Format ram://[ path] Examples
 * ram:///any/path/to/file.txt
 * 
 * mime This filesystem can read mails and its attachements like archives. If a
 * part in the parsed mail has no name, a dummy name will be generated. The
 * dummy name is: _body_part_X where X will be replaced by the part number. URI
 * Format mime:// mime-file-uri[! absolute-path] Examples
 * mime:file:///your/path/mail/anymail.mime!/
 * mime:file:///your/path/mail/anymail.mime!/filename.pdf
 * mime:file:///your/path/mail/anymail.mime!/_body_part_0
 * 
 * @author PengQingyang
 * @version [版本号, 2012-10-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
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
     * 判断对应文件是否存在
     * 
     * @param sftpFilePath
     * 
     * @return boolean true:存在|false:不存在
     * @exception IOException [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean exists(String sftpFilePath) throws IOException {
        if (StringUtils.isEmpty(sftpFilePath)) {
            throw new IOException("File '" + sftpFilePath + "' is empty.");
        }
        FileObject fileObj = null;
        try {
            fileObj = fsManager.resolveFile(sftpFilePath);
            return fileObj.exists();
        } catch (FileSystemException e) {
            throw fail(sftpFilePath, e);
        } finally {
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
     * <读取文件，将其中内容以byte进行输出> <功能详细描述>
     * 
     * @param sftpFilePath
     * @return
     * @throws IOException [参数说明]
     * 
     * @return byte[] [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static byte[] readFileToByteArray(String sftpFilePath)
            throws IOException {
        if (StringUtils.isEmpty(sftpFilePath)) {
            throw new IOException("File '" + sftpFilePath + "' is empty.");
        }
        FileObject fileObj = null;
        InputStream in = null;
        try {
            fileObj = fsManager.resolveFile(sftpFilePath);
            if (fileObj.exists()) {
                System.out.println(fileObj.getType().getName());
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("File '" + sftpFilePath
                            + "' exists but is a directory");
                } else {
                    in = fileObj.getContent().getInputStream();
                    return IOUtils.toByteArray(in);
                }
            } else {
                throw new FileNotFoundException("File '" + sftpFilePath
                        + "' does not exist");
            }
        } catch (FileSystemException e) {
            throw fail(sftpFilePath, e);
        } finally {
            IOUtils.closeQuietly(in);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
     * <读取文件内容> <功能详细描述>
     * 
     * @param sftpFilePath
     * @param encoding
     * @return
     * @throws IOException [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String readFileToString(String sftpFilePath, String encoding)
            throws IOException {
        if (StringUtils.isEmpty(sftpFilePath)) {
            throw new IOException("File '" + sftpFilePath + "' is empty.");
        }
        FileObject fileObj = null;
        InputStream in = null;
        try {
            fileObj = fsManager.resolveFile(sftpFilePath);
            if (fileObj.exists()) {
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("File '" + sftpFilePath
                            + "' exists but is a directory");
                } else {
                    in = fileObj.getContent().getInputStream();
                    return IOUtils.toString(in, encoding);
                }
            } else {
                throw new FileNotFoundException("File '" + sftpFilePath
                        + "' does not exist");
            }
        } catch (FileSystemException e) {
            throw fail(sftpFilePath, e);
        } finally {
            IOUtils.closeQuietly(in);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
     * <读取文件内容> <功能详细描述>
     * 
     * @param sftpFilePath
     * @return
     * @throws IOException [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static String readFileToString(String sftpFilePath)
            throws IOException {
        return readFileToString(sftpFilePath, null);
    }
    
    /**
     * <读取文件内容> <功能详细描述>
     * 
     * @param sftpFilePath
     * @param encoding
     * @return
     * @throws IOException [参数说明]
     * 
     * @return List<String> [返回类型说明]
     * @exception throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    public static List<String> readLines(String sftpFilePath, String encoding)
            throws IOException {
        if (StringUtils.isEmpty(sftpFilePath)) {
            throw new IOException("File '" + sftpFilePath + "' is empty.");
        }
        FileObject fileObj = null;
        InputStream in = null;
        try {
            fileObj = fsManager.resolveFile(sftpFilePath);
            if (fileObj.exists()) {
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("File '" + sftpFilePath
                            + "' exists but is a directory");
                } else {
                    in = fileObj.getContent().getInputStream();
                    return IOUtils.readLines(in, encoding);
                }
            } else {
                throw new FileNotFoundException("File '" + sftpFilePath
                        + "' does not exist");
            }
        } catch (FileSystemException e) {
            throw fail(sftpFilePath, e);
        } finally {
            IOUtils.closeQuietly(in);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
     * 
     * 读取 sftp 文件内容
     * 
     * @param sftpFilePath sftp 文件路径
     * 
     * @return List<String> 文件内容
     * @exception throws IOException IO 异常
     * @see [类、类#方法、类#成员]
     */
    public static List<String> readLines(String sftpFilePath)
            throws IOException {
        return readLines(sftpFilePath, null);
    }
    
    /**
     * 
     * 复制文件到 sftp 中
     * 
     * @param sftpFilePath sftp 路径
     * @param sourceFilePath 预复制文件路径
     * 
     * @throws IOException IO 异常
     * @see [类、类#方法、类#成员]
     */
    public static void copyFileToFile(String sftpFilePath, String sourceFilePath)
            throws IOException {
        if (StringUtils.isEmpty(sftpFilePath)) {
            throw new IOException("File '" + sftpFilePath + "' is empty.");
        }
        FileObject fileObj = null;
        FileObject sourceFileObj = null;
        
        try {
            fileObj = fsManager.resolveFile(sftpFilePath);
            sourceFileObj = fsManager.resolveFile(sourceFilePath);
            
            if (fileObj.exists()) {
                throw new IOException("File '" + sftpFilePath + "' is exists.");
            }
            
            fileObj.copyFrom(sourceFileObj, new FileTypeSelector(FileType.FILE));
            
        } catch (FileSystemException e) {
            throw fail(sftpFilePath, e);
        } finally {
            if (fileObj != null) {
                fileObj.close();
            }
            if (sourceFileObj != null) {
                sourceFileObj.close();
            }
        }
    }
    
    /**
     * 
     * 将内容写入文件中<br/>
     * 1.如果文件不存在，则创建<br/>
     * 2.如果 sftp 没有写入权限，则调用失败
     * 
     * @param sftpFilePath sftp 路径
     * @param data 需要写入的数据
     * @param encoding 文件编码
     * 
     * @throws IOException IO 异常
     * @see [类、类#方法、类#成员]
     */
    public static void writeStringToFile(String sftpFilePath, String data,
            String encoding) throws IOException {
        if (StringUtils.isEmpty(sftpFilePath)) {
            throw new IOException("File '" + sftpFilePath + "' is empty.");
        }
        FileObject fileObj = null;
        OutputStream out = null;
        
        try {
            fileObj = fsManager.resolveFile(sftpFilePath);
            
            if (!fileObj.exists()) {
                fileObj.createFile();
            } else {
                if (FileType.FOLDER.equals(fileObj.getType())) {
                    throw new IOException("Write fail. File '" + sftpFilePath
                            + "' exists but is a directory");
                }
            }
            
            if (!fileObj.isWriteable()) {
                throw new IOException("Write fail. File '" + sftpFilePath
                        + "' exists but isWriteable is false.");
            }
            
            out = fileObj.getContent().getOutputStream();
            IOUtils.write(data, out, encoding);
        } catch (FileSystemException e) {
            throw fail(sftpFilePath, e);
        } finally {
            IOUtils.closeQuietly(out);
            if (fileObj != null) {
                fileObj.close();
            }
        }
    }
    
    /**
     * 
     * 抛出错误异常
     * 
     * @param sftpFilePath
     * @param e
     * @throws IOException
     * 
     * @return void [返回类型说明]
     * @throws throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     */
    private static final IOException fail(String sftpFilePath,
            FileSystemException e) {
        return new IOException("File '" + sftpFilePath + "' resolveFile fail."
                + "[" + e.getMessage() + "]", e);
    }
    
    /**
     * 
     * 将内容写入文件中<br/>
     * 1.如果文件不存在，则创建<br/>
     * 2.如果 sftp 没有写入权限，则调用失败<br/>
     * 3.文件编码为 utf-8
     * 
     * @param sftpFilePath sftp 路径
     * @param data 需要写入的数据
     * 
     * @exception throws IOException IO 异常
     * @see [类、类#方法、类#成员]
     */
    public static void writeStringToFile(String sftpFilePath, String data)
            throws IOException {
        writeStringToFile(sftpFilePath, data, null);
    }
    
    public static void main(String[] args) throws Exception {
        VFSUtils.writeStringToFile("sftp://yxxd:yxxd@113.200.27.110:2021/11222.txt",
                "测试",
                "UTF-8");
        //        VFSUtils.writeStringToFile("sftp://yxxd:yxxd@113.200.27.110:2021/1333.txt",
        //                "11111111111",
        //                "UTF-8");
        
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
        
        //        FileObject tt = fsManager.resolveFile("D:/test/createfilefolder1/file1.txt");
        //        tt.createFile();
        //        System.out.println(tt instanceof Closeable);
        
    }
}
