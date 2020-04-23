/**
 * 
 */
package com.hp.core.excel.helper;
/**
 * excel导入导出工具类
 * @author huangping
 * Apr 25, 2019
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hp.core.common.batch.BatchOpera;
import com.hp.core.common.batch.BatchOpera.Callback;
import com.hp.core.common.beans.IdNameBean;
import com.hp.core.excel.bean.ExcelColumnBean;

public class ExcelHelper {
	
	private static Logger log = LoggerFactory.getLogger(ExcelHelper.class);
	
	private static final String XLSX = "xlsx";
	
	/**
	 * 每个sheet最大行数
	 */
	private static final int MAX_SHEET_ROWS = 50000;
	
	/**
	 * 默认sheet名称
	 */
	private static final String DEFAULT_SHEET_NAME = "sheet";
	
	/**
	 * 生成excel
	 * @param <T>
	 * @param out
	 * @param columns
	 * @param dataList
	 */
	public static <T> void createExcel(OutputStream out, List<ExcelColumnBean> columns, List<T> dataList) {
		createExcel(out, columns, dataList, DEFAULT_SHEET_NAME);
	}
	
	/**
	 * 生成excel
	 * @param <T>
	 * @param out
	 * @param columns
	 * @param dataList
	 * @param sheetName
	 */
	@SuppressWarnings("deprecation")
	public static <T> void createExcel(OutputStream out, List<ExcelColumnBean> columns, List<T> dataList, String sheetName) {
		if (CollectionUtils.isEmpty(dataList)) {
			return;
		}
		Workbook wb = XSSFWorkbookFactory.createWorkbook();
		try {
			BatchOpera.batch(dataList, MAX_SHEET_ROWS, new Callback<T, Object>() {
				@Override
				public List<Object> callback(List<T> list, int currentPage, Object... params) {
					createSheet(wb, columns, list, sheetName + "_" + currentPage);
					return null;
				}
			});
			wb.write(out);
			out.flush();
		} catch (Exception e) {
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(out);
		}
	}
	
	/**
	 * 创建sheet
	 * @param <T>
	 * @param wb
	 * @param columns
	 * @param list
	 * @param sheetName
	 * @return
	 */
	private static <T> Sheet createSheet(Workbook wb, List<ExcelColumnBean> columns, List<T> list, String sheetName) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		
		//sheet
		Sheet sheet = wb.createSheet(sheetName);
		
		//样式
		CellStyle style = wb.createCellStyle();
		DataFormat format = wb.createDataFormat();
		style.setDataFormat(format.getFormat("@"));
		
		Row row = null;
		Cell cell = null;

		if (CollectionUtils.isEmpty(columns)) {
			return null;
		}

		//创建表头
		row = sheet.createRow(0);
		for (int i = 0; i < columns.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(columns.get(i).getTitle());
			cell.setCellStyle(style);
		}

		//创建内容
		T t = null;
		Object value = null;
		for (int i = 0; i < list.size(); i++) {
			t = list.get(i);
			if (t == null) {
				continue;
			}
			
			row = sheet.createRow(i + 1);
			for (int j = 0 ; j < columns.size(); j++) {
				cell = row.createCell(j);
				cell.setCellStyle(style);
				try {
					value = BeanUtils.getProperty(t, columns.get(j).getField());
				} catch (Exception e) {
				}
				cell.setCellValue(value == null ? "" : value.toString());
			}
		}
		return sheet;
	}
	
	/**
	 * 读取excel文件
	 * @param url
	 * @param readFirstLine
	 * @return
	 */
	public static List<String[]> readExcelFromRemoteUrl(String url, boolean readFirstLine) {
		try (
				InputStream inputStream = new URL(url).openStream();
		) {
			String ext = StringUtils.substringAfterLast(url, ".");
			boolean xssf = StringUtils.equalsIgnoreCase(ext, XLSX);
			return readExcel(inputStream, readFirstLine, xssf);
		} catch (Exception e) {
			log.error("readExcelFromRemoteUrl error. with url={}", url);
			return null;
		}
	}
	
	/**
	 * 读取excel文件
	 * @param file
	 * @param readFirstLine
	 * @return
	 */
	public static List<String[]> readExcel(File file, boolean readFirstLine) {
		try (
				InputStream inputStream = new FileInputStream(file);
		) {
			String ext = StringUtils.substringAfterLast(file.getName(), ".");
			boolean xssf = StringUtils.equalsIgnoreCase(ext, XLSX);
			return readExcel(inputStream, readFirstLine, xssf);
		} catch (Exception e) {
			log.error("readExcel error. with filaName={}", file.getName());
			return null;
		}
	}
	
	/**
	 * 读取excel文件
	 * @param fileName
	 * @param readFirstLine
	 * @return
	 */
	public static List<String[]> readExcel(String fileName, boolean readFirstLine) {
		try (
				InputStream inputStream = new FileInputStream(fileName);
		) {
			String ext = StringUtils.substringAfterLast(fileName, ".");
			boolean xssf = StringUtils.equalsIgnoreCase(ext, XLSX);
			return readExcel(inputStream, readFirstLine, xssf);
		} catch (Exception e) {
			log.error("readExcel error. with filaName={}", fileName);
			return null;
		}
	}
	
	/**
	 * 读取excel文件
	 * @param inputStream
	 * @param readFirstLine
	 * @param xssf
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<String[]> readExcel(InputStream inputStream, boolean readFirstLine, boolean xssf) {
		List<String[]> respList = null;
		Workbook workbook = null;
		try {
			workbook = getWorkbook(inputStream, xssf);
			Sheet sheet = workbook.getSheetAt(0);
			if (sheet == null) {
				return null;
			}
			
			int lastRowNum = sheet.getLastRowNum();
			int startIndex = 0;
			if (!readFirstLine) {
				startIndex = 1;
			}
			respList = new ArrayList<>();
			String[] obj = null;
			int lastCellNum = 0;
			Row row = null;
			Cell cell = null;
			DataFormatter formatter = new DataFormatter();
			for (int i = startIndex; i <= lastRowNum; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				lastCellNum = row.getLastCellNum();
				obj = new String[lastCellNum + 1];
				for (int j = 0; j <= lastCellNum; j++) {
					cell = row.getCell(j);
					if (cell == null) {
						obj[j] = StringUtils.EMPTY;
						continue;
					}
					obj[j] = formatter.formatCellValue(cell).trim();
					//obj[j] = cell.getStringCellValue().trim();
				}
				respList.add(obj);
			}
		} catch (Exception e) {
			log.error("readExcel error.", e);
		} finally {
			IOUtils.closeQuietly(workbook);
		}
		return respList;
	}
	
	/**
	 * 获取工作表
	 * @param inputStream
	 * @param xssf
	 * @return
	 */
	private static Workbook getWorkbook(InputStream inputStream, boolean xssf) {
		try {
			if (xssf) {
				return new XSSFWorkbook(inputStream);
			} else {
				return new HSSFWorkbook(inputStream);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	public static void main(String[] args) {
//		try (
//			InputStream in = new FileInputStream("/Users/huangping/Desktop/1.xlsx");
//		) {
//			List<String[]> list = readExcel(in, false);
//			System.out.println(JSON.toJSONString(list));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		String url = "/Users/huangping/Downloads/2020.2月明细.xls";
		log.info(JSON.toJSONString(readExcel(url, true)));
		
	}
	
	public static void main1(String[] args) {
		OutputStream out = null;
		try {
			out = new FileOutputStream("./excel.xlsx");
			List<ExcelColumnBean> columns = Lists.newArrayList(new ExcelColumnBean("id", "id"), new ExcelColumnBean("名称", "name"));
			List<IdNameBean> dataList = Lists.newArrayList(new IdNameBean(1, "黄平11121阿拉斯加的垃圾堆里"), new IdNameBean(2, "name2加的垃圾堆里"), new IdNameBean(3, "黄平3加的垃圾堆里加的垃圾堆里"));
			createExcel(out, columns, dataList, "sheet");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
