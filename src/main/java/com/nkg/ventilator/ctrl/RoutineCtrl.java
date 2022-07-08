package com.nkg.ventilator.ctrl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nkg.ventilator.service.APIAuthService;
import com.nkg.ventilator.service.XyzDescExportService;
import com.nkg.ventilator.service.APIAuthService.BU;
import com.nkg.ventilator.util.Encrypt;

@RestController
@RequestMapping(value = "/routine")
public class RoutineCtrl extends BaseController {

	@Autowired private XyzDescExportService xyzDescExportService;
	@Autowired private APIAuthService apiAuthService;

	//機台商品文字敘述
	@RequestMapping(value = "/productDescExport",
			method = RequestMethod.POST)
	public ResponseEntity<Resource> productDescExport() throws Exception {

		//Perform export
		Path path = xyzDescExportService.buildProductDescXLSX();
		File file = path.toFile();

		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

	//機台商品文字敘述
	@RequestMapping(value = "/sparePartExport",
			method = RequestMethod.POST)
	public ResponseEntity<Resource> sparePartExport(HttpServletResponse resp) throws Exception {

		//Perform export
		Path path = xyzDescExportService.buildSparePartXLSX();
		File file = path.toFile();

		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
	}

	//apiauth 狀態
	@RequestMapping(value = "/apiauth/status",
			method = RequestMethod.GET)
	public String apiauthStatus() {

		return gson.toJson(apiAuthService.getStatus());
	}

	//停止 apiauth schedule
	@RequestMapping(value = "/apiauth/stop",
			method = RequestMethod.GET)
	public String apiauthStop() {

		return gson.toJson(apiAuthService.stopSchedule());
	}

	//恢復 apiauth schedule
	@RequestMapping(value = "/apiauth/recover",
			method = RequestMethod.GET)
	public String apiauthRecover() {

		return gson.toJson(apiAuthService.recoverSchedule());
	}

	//更新 apiauth key
	@RequestMapping(value = "/apiauth/update",
			method = RequestMethod.POST)
	public String apiauthUpdate(
			@RequestParam String new_pwd,
			@RequestParam String old_pwd) throws Exception {

		String cipherNewPwd = Encrypt.enc(new_pwd);
		String cipherOldPwd = Encrypt.enc(old_pwd);

		HashMap<BU, Integer> result = apiAuthService.updateKey(cipherNewPwd, cipherOldPwd);


		return gson.toJson(new Result(
				cipherNewPwd,
				cipherOldPwd,
				result));
	}

	static class Result {
		public String cipherNewPwd;
		public String cipherOldPwd;
		public HashMap<BU, Integer> updateRes;
		public boolean isAccomplished;

		public Result(String cipherNewPwd, String cipherOldPwd, HashMap<BU, Integer> updateRes) {
			this.cipherNewPwd = cipherNewPwd;
			this.cipherOldPwd = cipherOldPwd;
			this.updateRes = updateRes;
			this.isAccomplished = (1 == updateRes.get(BU.XYZ) && 1 == updateRes.get(BU.HIMIRROR));
		}
	}
}
