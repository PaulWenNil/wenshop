package com.wen.shop.web.servlet;

import com.wen.shop.constant.Constant;
import com.wen.shop.dao.OperationDao;
import com.wen.shop.dao.impl.OperationDaoImpl;
import com.wen.shop.domain.Category;
import com.wen.shop.domain.Operation;
import com.wen.shop.domain.Product;
import com.wen.shop.domain.Seller;
import com.wen.shop.service.ProductService;
import com.wen.shop.service.impl.ProductServiceImpl;
import com.wen.shop.utils.UUIDUtils;
import com.wen.shop.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "EditProductServlet", value = "/editProduct")
public class EditProductServlet extends HttpServlet {
    //修改商品
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //使用fileupload保存图片和将商品的信息放入map
            //创建map集合 存放商品的信息
            Map<String, Object> map = new HashMap<>();

            //创建磁盘文件项工厂 设置临时文件大小和位置
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //创建核心上传对象
            ServletFileUpload upload = new ServletFileUpload(factory);

            //解析request
            List<FileItem> list = upload.parseRequest(request);

            //遍历list 获取每一个文件项
            for (FileItem fi: list
            ) {
                //获取name属性值
                String key = fi.getFieldName();

                //判断是否是普通上传组件
                if(fi.isFormField()){
                    //普通
                    map.put(key,fi.getString("utf-8"));
                }
                else{
                    //文件
                    //获取文件名称
                    String name = fi.getName();

                    //获取文件真实名称
                    String realName = UploadUtils.getRealName(name);

                    //获取文件的随机名称
                    String uuidName = UploadUtils.getUUIDName(realName);

                    //获取随机目录
                    String dir = UploadUtils.getDir();

                    //获取文件内容（输入流）
                    InputStream is = fi.getInputStream();

                    //创建输出流
                    //获取products目录的真实路径
                    String productPath = getServletContext().getRealPath("/products");

                    //创建随机目录
                    File dirFile = new File(productPath, dir);
                    if(!dirFile.exists()){
                        dirFile.mkdirs();
                    }
                    FileOutputStream os = new FileOutputStream(new File(dirFile, uuidName));

                    //对拷流
                    IOUtils.copy(is,os);

                    //释放资源
                    os.close();
                    is.close();

                    //删除临时文件
                    fi.delete();

                    //将商品路径放入map中
                    map.put(key,"products"+dir+"/"+uuidName);
                }
            }

            //用session来获取pid
            HttpSession session = request.getSession();
            String pid =  (String)session.getAttribute("pid");

            //调用productservice获取商品信息
            ProductService ps = new ProductServiceImpl();
            Product p = ps.getById(pid);

            //设置session的pid为空
            session.setAttribute("pid",null);

            //使用beanutils封装
            BeanUtils.populate(p,map);

            //手动设置category
            Category c = new Category();
            c.setCid((String)map.get("cid"));
            p.setCategory(c);

            //调用service完成保存操作
            ps.edit(p);

            OperationDao operationDao = new OperationDaoImpl();
            Seller seller = (Seller) session.getAttribute("seller");
            String detail = "Edit product: "+ p.getPname();

            String operation_id = UUIDUtils.getId();
            Long time = System.currentTimeMillis();
            Operation operation = new Operation(seller.getSid(), detail, time, operation_id);
            operationDao.insert(operation);

            //重定向
            response.sendRedirect(request.getContextPath()+"/adminProduct?method=findAll");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改商品失败");
        }
    }
}
