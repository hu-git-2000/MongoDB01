package com.bin.controller.admin;

import com.bin.bean.Customer;
import com.bin.bean.MasterAddress;
import com.bin.bean.Order;
import com.bin.bean.gukejilu;
import com.bin.service.Customerservice;
import com.bin.service.Masterservice;
import com.bin.service.Orderservice;
import com.bin.service.gukejilusservice;
import com.bin.util.JsonData;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单的操作  列表展示
 */
@Controller
@RequestMapping("/order")
public class OrderController {
@Autowired
private Orderservice orderservice;

    @Autowired
    private Customerservice Customerservice;

    @Autowired
    private gukejilusservice gukejilusservice;


    @Autowired
    private Masterservice masterservice;



@RequestMapping("/orders")
@ResponseBody
    public Object getorders (String phone, String status,@RequestParam(value = "page",defaultValue = "1")Integer page, @RequestParam(value = "limit",defaultValue = "5")Integer limit){
            Map<String,Object> map=new HashMap<String, Object>();
            if (!StringUtils.isEmpty(phone)){
                   map.put("phone",phone);
            }
          if (!StringUtils.isEmpty(status)){
                map.put("status",status);
          }
    //PageInfo<Order> pageInfo=Orderservice.findbypages(map,page,limit);
             PageInfo<Order> pageInfo=orderservice.findbypages(map,page,limit);
            return JsonData.buildSuc(pageInfo);
    }


    @RequestMapping("/selectList")
    public  String index(){
        return "/static/html/orders/table.html";
    }
@RequestMapping("/adds")
@ResponseBody
    public  Object adds(Order order,HttpSession httpsession){
        //获取session中的手机号
        String phone = (String) httpsession.getAttribute("phone");
        System.out.println(phone);
        //1.先根据手机号 查询一下当前 订单状态
        List<Order> orders = orderservice.findByPhone(phone);
        if (null == orders || orders.size() == 0) {//当前手机号未下单，表示首次下单
            //保存数据到customer和orders
            int x = Customerservice.saveCustomers(phone);
            order.setPhone(phone);
            int y = orderservice.saveorders(order);
            if (x > 0 && y > 0) {
                return "success";
            }
        } else {
            Order order1 = orders.get(orders.size() - 1);
                //如果状态是3或4就可以添加
                System.out.println(order1.getStatus());

                if (order1.getStatus().equals("3")) {
                    order.setPhone(phone);
                    int y = orderservice.saveorders(order);
                    if (y > 0) {
                        return "success";
                    }
                }else {
                    return "error";
                }
            }
    return "error";
    }

//后台添加
@RequestMapping("/add")
 public  String  addorder(){
     return "/static/html/orders/addOrder.html";
 }

    @RequestMapping("/addOrde")
    @ResponseBody
 public Object addorders(Order order,String name,String car){
//1.根据手机号查询状态
    // String  phone= (String) httpsession.getAttribute("phone");
    List <Order> order1= orderservice.findByPhone(order.getPhone());
   if((null == order1 || order1.size() == 0)){
    int x= Customerservice.saveCustomer(order.getPhone(),new Date(),name,car);
       int y=orderservice.saveorder(order);
       if (x>0&&y>0){
           return "success";
       }
   }else {
       for (Order o:order1) {
            if (o.getStatus().equals("3")||o.getStatus().equals("4")){
                int x=  Customerservice.saveCustomer(order.getPhone(),new Date(),name,car);
                int y=orderservice.saveorder(order);
                if (x>0&&y>0){

                    return "success";
                }
            }

       }

   }
     return "error";
 }
 @RequestMapping("/sendMaster")
 public  String sendMaster(){
     return "/static/html/orders/master/table.html";
 }

    @RequestMapping("/Masterupdate")
    @ResponseBody
    public Object Masterupdate(Integer id,Integer mid){
       Order order= orderservice.findBylist(id);
        System.out.println(order.getStatus());
       if (order.getStatus().equals("3")||order.getStatus().equals("4")){
           return 0;
       }else {
           //调用业务层 更新订单的状态 如果订单更新完成   在更新维修工o
           int i= orderservice.updateorderbyid(mid,id);
           if (i>0) {
               //更新维修工
               int x= masterservice.updetamasteraddress(mid);
               return x;
           }
       }
            return 0;
        }

//删除
    @RequestMapping("deleMaster")
   @ResponseBody
    public Object deleteMaster (Integer id) {
        //System.out.println( mid+"---------"+id);
        int x=orderservice.relieveMaster(id);
        List<MasterAddress> list=masterservice.listMasterAddress();
        for (MasterAddress m:list) {
            int y=masterservice.relieveMaster(m.getId());
            return y;
        }


        return 0;
    }

    @RequestMapping("supdorder")
    public String supdorder(){
        return "/static/html/orders/updOrder.html";
    }


    @RequestMapping("upd")
    @ResponseBody
    public Object oupd(Order order,Integer id){
       if (orderservice.upd(order,id)>0){
           System.out.println(order.getPhone());
       }
        return "error";

    }
    @RequestMapping("qlist")
    public String oupd(){
        return "/static/html/customerPhone/html/order.html";
    }

    @RequestMapping("login")
    public String loginx(){
        return "/static/html/customerPhone/index.html";
    }

    //前台列表
    @RequestMapping("/selectListByPhone")
    @ResponseBody
    public Object selectListByPhone(HttpSession session){
        //获取session里面的手机号
        String phone = (String) session.getAttribute("phone");
        System.out.println("    "+phone);
        List<Order> list=orderservice.findByPhone(phone);
       for (Order o:list){
           System.out.println(o.toString());
       }
        return list;
    }



    @RequestMapping("/down")
    @ResponseBody
    public void exceldown() throws Exception {
//1.创建workbook
        System.out.println("abc");
        Workbook wb=new HSSFWorkbook();
        //2创建sheet
        Sheet sh = wb.createSheet();
        //3创建row  首行
        Row row = sh.createRow(0); //0代表第1行 , 1是第2行 ,以此类推....
        /*订单号 客户 手机号 下单时间 地址  状态*/
        //4.创建 存放表头的  cell单元格
        Cell[] cell=new Cell[6];
        for (int i = 0; i <cell.length; i++) {
            cell[i] = row.createCell(i);
        }
        cell[0].setCellValue("订单号");
        cell[1].setCellValue("客户");
        cell[2].setCellValue("手机号");
        cell[3].setCellValue("下单时间");
        cell[4].setCellValue("地址");
        cell[5].setCellValue("状态");

        //获取表单数据冲第二行开始 所有的数据
        List<Order> list= orderservice.list();
        for (int i = 0; i <list.size() ; i++) {
            Row row1 = sh.createRow(i + 1);//从第2行还是创建  行
            Cell [] c=new Cell[6];
            for (int j = 0; j <c.length ; j++) {//创建其他行(第1行除外)里面的单元格
                c[j] = row1.createCell(j);//创建6个单元格防盗其他行
            }
            //填充数据
            Order order=list.get(i);
            c[0].setCellValue(order.getId());
            c[1].setCellValue(order.getCustomer().getName());
            c[2].setCellValue(order.getPhone());
            c[3].setCellValue(order.getContents());
            c[4].setCellValue(order.getAddress());

            //0刚下单，1已接单，2已到达正在修，3已完成，4已评价
            String status = order.getStatus();
            String str="";
            if (status.equals("0")){
                str="刚下单";
            }
            if (status.equals("1")){
                str="已结单";
            }
            if (status.equals("2")){
                str="已到达正在修";
            }
                if (status.equals("3")){
                    str="已完成";
                }
            c[5].setCellValue(str);
        }
        OutputStream os=new FileOutputStream("D:\\桌面\\work456.xls");
        wb.write(os);
    }



    @RequestMapping("updateStatus")
    public String updStatus(){
        return "/static/html/orders/updStatus.html";
    }


    @RequestMapping("updstatus")
    public Object updatestatus(Integer id,String status)   {
        System.out.println("-----------------------abc");
        System.out.println(id+status);
       int i= orderservice.updatestatus(id,status);
       if (i>0){
           return "success";
       }
        return "error";
    }

/*测试*/
    @RequestMapping("/orderss")
    @ResponseBody
    public Object gukejilucs (String phone, String gname,@RequestParam(value = "page",defaultValue = "1")Integer page, @RequestParam(value = "limit",defaultValue = "5")Integer limit){
        Map<String,Object> map=new HashMap<String, Object>();
        if (!StringUtils.isEmpty(phone)){
            map.put("phone",phone);
        }
        if (!StringUtils.isEmpty(gname)){
            map.put("gname",gname);
        }

        PageInfo<gukejilu> pageInfo= gukejilusservice.findbypagess(map,page,limit);
        return JsonData.buildSuc(pageInfo);
    }

}

