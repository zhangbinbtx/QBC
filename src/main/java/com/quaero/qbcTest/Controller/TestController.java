package com.quaero.qbcTest.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.dto.entity.AlarmInfo;
import com.quaero.qbcTest.dto.entity.AlarmInfoDetail;
import com.quaero.qbcTest.dto.entity.Alarmcode;
import com.quaero.qbcTest.server.QbcTestApi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/qbcTest")
public class TestController {
	@Autowired
	private QbcTestApi api; //装配服务层接口
    //客户端主动发送消息到服务端，服务端马上回应指定的客户端消息
    //类似http无状态请求，但是有质的区别
    //websocket可以从服务器指定发送哪个客户端，而不像http只能响应请求端
    
 

	/**
	 * 获取上下位机错误图表
	 * 
	 * @return
	 * @throws SQLException
	 */
	@GetMapping("/getErrorCount")
	public ResponseVO getErrorCount() throws SQLException {
	//public List<AlarmInfo> getErrorCount() throws SQLException {
		log.info("进入getErrorCount");
		List<AlarmInfo> aa = api.getErrorMap(); //调用服务层接口
		return new ResponseVO(aa);
	}

	/**
	 * 获取下位机各类错误码
	 * 
	 * @param content
	 * @return
	 * @throws SQLException
	 */
	@GetMapping("/UpAndDown/{content}")
	public List<Object> getUpAndDown(@PathVariable("content") String content) throws SQLException {
		System.out.println("进入getUpAndDown");
		List<AlarmInfoDetail> cp = api.getUpAndDown();// 故障列表
		List<Alarmcode> aa = api.getErrorGroupTime();// 设备故障,CODE\NUM\SAVEDATE
		String[] maincode = { "1-1-0", "1-2-0", "1-3-1", "1-3-2", "1-3-3", "1-3-4", "1-3-5", "1-3-6", "1-3-7", "1-5-2",
				"1-5-3", "1-6-1", "1-6-2", "1-6-3", "1-6-4", "1-6-5", "1-6-6", "1-6-7", "1-7-1" };
		String[] recode = { "1-11-0", "1-11-1", "1-11-2", "1-11-3", "1-11-4", "1-11-5", "1-11-6", "1-11-7", "1-11-8",
				"1-11-9", "1-11-10", "1-11-11", "1-11-12" };
		List<String> list = new ArrayList<String>();// code
		List<String> list1 = new ArrayList<String>();// 日期
		List<Object> list3 = new ArrayList<Object>();
		for (Alarmcode str : aa) {
			String ckp = str.getCODE();
			String date = str.getSAVEDATE();
			System.out.println("code=" + ckp);
			if (Arrays.asList(maincode).contains(ckp) && content.equals("主控板故障")) {// 主控板故障个数
				list.add(ckp);// 名称
				list1.add(date);// 时间
			}
			if (Arrays.asList(recode).contains(ckp) && content.equals("交流故障")) {// 交流故障个数
				list.add(ckp);// 名称
				list1.add(date);// 时间
			} else {
				if (ckp.substring(0, 3).contains("32-") && content.equals("中位机故障")) {// 中位机故障个数
					list.add(ckp);// 名称
					list1.add(date);// 时间
				} else {
					String ccd = ckp.substring(0, 2);
					if (ccd.contains("2-") && content.equals("反应板故障")) {// 反应板
						list.add(ckp);// 名称
						list1.add(date);// 时间
					} else if (ccd.contains("4-") && content.equals("搅拌板故障")) {// 搅拌板
						list.add(ckp);// 名称
						list1.add(date);// 时间
					} else if (ccd.contains("5-") && content.equals("样本板故障")) {// 样本板
						list.add(ckp);// 名称
						list1.add(date);// 时间
					} else if (ccd.contains("6-") && content.equals("试剂1板故障")) {// 试剂1板
						list.add(ckp);// 名称
						list1.add(date);// 时间
					} else if (ccd.contains("7-") && content.equals("试剂2板故障")) {// 试剂2板
						list.add(ckp);// 名称
						list1.add(date);// 时间
					} else {
						if (content.equals("其他故障")) {
							list.add(ckp);// 名称
							list1.add(date);// 时间
						}
					}
				}
			}
		}
		List<String> list4 = list.stream().distinct().collect(Collectors.toList());// 去重
		List<String> list5 = list1.stream().distinct().collect(Collectors.toList());// 去重
		List<Object> list6 = new ArrayList<Object>();
		for (String se : list4) {
			nameData nameData = new nameData();
			List<Integer> inte = new ArrayList<Integer>();
			nameData.setName(se);
			for (Alarmcode str : aa) {
				String ckp = str.getCODE();
				int num = str.getCount();
				String date = str.getSAVEDATE();
				if (se.equals(ckp)) {
					for (String sn : list5) {
						if (sn.equals(date)) {
							inte.add(num);
						} else {
							inte.add(0);
						}
					}
				}
			}
			nameData.setData(inte);
			list6.add(nameData);
		}
		list3.add(list4);
		list3.add(list5);
		list3.add(list6);
		return list3;
	}

	/**
	 * 按日期区分错误
	 * 
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	@GetMapping("/UpDownType/{type}")
	public List UpDownType(@PathVariable("type") int type) throws SQLException {
		System.out.println("进入UpDownType");
		List<AlarmInfoDetail> cp = api.getUpAndDown();// 故障列表
		List<AlarmInfo> aa = api.getErrorMap();// 设备故障
		List<AlarmInfoDetail> list1 = new ArrayList<AlarmInfoDetail>();
		List<AlarmInfoDetail> list2 = new ArrayList<AlarmInfoDetail>();
		for (int i = 0; i < cp.size(); i++) {
			AlarmInfoDetail alar = cp.get(i);
			String code = alar.getCode();
			String[] codes = code.split("-");
			if (codes.length == 2) {
				list1.add(alar);
			} else if (codes.length == 3) {
				list2.add(alar);
			}
		}
		String[] maincode = { "1-1-0", "1-2-0", "1-3-1", "1-3-2", "1-3-3", "1-3-4", "1-3-5", "1-3-6", "1-3-7", "1-5-2",
				"1-5-3", "1-6-1", "1-6-2", "1-6-3", "1-6-4", "1-6-5", "1-6-6", "1-6-7", "1-7-1" };
		String[] recode = { "1-11-0", "1-11-1", "1-11-2", "1-11-3", "1-11-4", "1-11-5", "1-11-6", "1-11-7", "1-11-8",
				"1-11-9", "1-11-10", "1-11-11", "1-11-12" };
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<keyValue> keylabel = new ArrayList<keyValue>();

		if (type == 0) {
			int k = 0; // 上位机故障个数
			int l = 0; // 下位机故障个数
			for (AlarmInfo str : aa) {
				String code = str.getCODE();
				for (int i = 0; i < list1.size(); i++) {
					if (code.equals(list1.get(i).getCode())) {
						k = k + 1;
					}
				}
				for (int i = 0; i < list2.size(); i++) {
					if (code.equals(list2.get(i).getCode())) {
						l = l + 1;
					}
				}
			}
			keyValue kl = new keyValue();
			kl.setName("上位机故障");
			kl.setValue(k);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("下位机故障");
			kl.setValue(l);
			keylabel.add(kl);
			// map.put("上位机故障",k);
			// map.put("下位机故障",l);
		} else if (type == 1) {

			int i = 0; // 主控板故障个数
			int j = 0; // 交流故障个数
			int x = 0; // 中位机故障个数
			int y = 0; // 反应板故障个数
			int z = 0; // 搅拌板故障个数
			int p = 0; // 搅拌板故障个数
			int m = 0; // 样本板故障个数
			int n = 0; // 样本板故障个数
			int o = 0; // 其他故障个数
			for (AlarmInfoDetail str : list2) {
				String cod = str.getCode();
				if (cod != null) {
					if (Arrays.asList(maincode).contains(cod)) {
						for (AlarmInfo strp : aa) {
							if (strp.getCODE().equals(cod)) {
								i = i + 1;
							}
						}
					}
					if (Arrays.asList(recode).contains(cod)) {
						for (AlarmInfo strp : aa) {
							if (strp.getCODE().equals(cod)) {
								j = j + 1;
							}
						}
					} else {
						if (cod.substring(0, 3).contains("32-")) {
							for (AlarmInfo strp : aa) {
								if (strp.getCODE().equals(cod)) {
									x = x + 1;
								}
							}
						} else {
							String ccd = cod.substring(0, 2);
							System.out.println("ccd=" + ccd);
							if (ccd.contains("2-")) {// 反应板
								for (AlarmInfo strp : aa) {
									if (strp.getCODE().equals(cod)) {
										y = y + 1;
									}
								}
							} else if (ccd.contains("4-")) {// 搅拌板
								for (AlarmInfo strp : aa) {
									if (strp.getCODE().equals(cod)) {
										z = z + 1;
									}
								}
							} else if (ccd.contains("5-")) {// 样本板
								for (AlarmInfo strp : aa) {
									if (strp.getCODE().equals(cod)) {
										p = p + 1;
									}
								}
							} else if (ccd.contains("6-")) {// 试剂1板
								for (AlarmInfo strp : aa) {
									if (strp.getCODE().equals(cod)) {
										m = m + 1;
									}
								}
							} else if (ccd.contains("7-")) {// 试剂2板
								for (AlarmInfo strp : aa) {
									if (strp.getCODE().equals(cod)) {
										n = n + 1;
									}
								}
								System.out.println("n=" + n);
							} else {
								for (AlarmInfo strp : aa) {
									if (strp.getCODE().equals(cod)) {
										o = o + 1;
									}
								}
							}
						}
					}
				}
			}
			keyValue kl = new keyValue();
			kl.setName("主控板故障");
			kl.setValue(i);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("交流故障");
			kl.setValue(j);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("中位机故障");
			kl.setValue(x);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("反应板故障");
			kl.setValue(y);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("搅拌板故障");
			kl.setValue(z);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("样本板故障");
			kl.setValue(p);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("试剂1板故障");
			kl.setValue(m);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("试剂2板故障");
			kl.setValue(n);
			keylabel.add(kl);
			kl = new keyValue();
			kl.setName("其他故障");
			kl.setValue(o);
			keylabel.add(kl);

			map.put("主控板", i);
			map.put("交流", j);
			map.put("中位机", x);
			map.put("反应板", y);
			map.put("搅拌板", z);
			map.put("样本板", p);
			map.put("试剂1板", m);
			map.put("试剂2板", n);
			map.put("其他", o);
			System.out.println("重复数据的个数：" + map.toString());

			// for(AlarmInfoDetail str:list1){
			// Integer i = 1; //定义一个计数器，用来记录重复数据的个数
			// if(map.get(str.getContent()) != null){
			// i=map.get(str.getContent())+1;
			// }
			// map.put(str.getContent(),i);
			// }
			// System.out.println("重复数据的个数："+map.toString());
			// }else if(type==2){
			// for(AlarmInfoDetail str:list2){
			// Integer i = 1; //定义一个计数器，用来记录重复数据的个数
			// if(map.get(str.getContent()) != null){
			// i=map.get(str.getContent())+1;
			// }
			// map.put(str.getContent(),i);
			// }
			// System.out.println("重复数据的个数："+map.toString());
		}
		// return JSONObject.toJSONString(map);
		return keylabel;
	}
	public static void main(String[] args) {
		// byte[] dataIn=new byte[1];
		// dataIn[0]=(byte) (0xff&0xff);
		// System.out.println(dataIn[0]);
		int value = 33;
		byte[] src = new byte[2];
		src[0] = (byte) (value & 0xFF);
		src[1] = (byte) ((value >> 8) & 0xFF);
		// src[2] = (byte) ((value>>16) & 0xFF);
		// src[3] = (byte) ((value>>24) & 0xFF);

		int value1 = (int) ((src[0] & 0xFF) | ((src[1] & 0xFF) << 8));
		// | ((src[2] & 0xFF)<<16)
		// | ((src[3] & 0xFF)<<24));
		System.out.println(value1);
	}
}

@Data
class keyValue {
	private String name;
	private int value;
}

@Data
class nameData {
	private String name;
	private List<Integer> data;
}

