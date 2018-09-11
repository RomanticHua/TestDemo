/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.a10616.testdemo.zxing.decoding;

import android.os.Handler;
import android.os.Looper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * This thread does all the heavy lifting of decoding the images. �����߳�
 */
final class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";
	private final DecodeHandlerInterface handlerInterface;

//	hints参数说明
//	OTHER	未指定作用，应用自定义，Object类型
//	PURE_BARCODE	Boolean类型，指定图片是一个纯粹的二维码
//	POSSIBLE_FORMATS	可能的编码格式，List类型
//	TRY_HARDER	花更多的时间用于寻找图上的编码，优化准确性，但不优化速度，Boolean类型
//	CHARACTER_SET	编码字符集，通常指定UTF-8
//	ALLOWED_LENGTHS	允许的编码数据长度 - 拒绝多余的数据。不懂这是什么，int[]类型
//	ASSUME_CODE_39_CHECK_DIGIT	CODE 39 使用，不关心
//	ASSUME_GS1	假设使用GS1编码来解析，不关心
//	RETURN_CODABAR_START_END	CODABAR编码使用，不关心
//	NEED_RESULT_POINT_CALLBACK	当解析到可能的结束点时进行回调
//	ALLOWED_EAN_EXTENSIONS	允许EAN或UPC编码有额外的长度，不关心

	private final Hashtable<DecodeHintType, Object> hints;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(DecodeHandlerInterface handlerInterface,
                 Vector<BarcodeFormat> decodeFormats, String characterSet,
                 ResultPointCallback resultPointCallback) {

		this.handlerInterface = handlerInterface;
		handlerInitLatch = new CountDownLatch(1);

		hints = new Hashtable<DecodeHintType, Object>(3);

		if (decodeFormats == null || decodeFormats.isEmpty()) {
			decodeFormats = new Vector<BarcodeFormat>();
			decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
		}

		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

		if (characterSet != null) {
			hints.put(DecodeHintType.CHARACTER_SET, characterSet);
		}

		hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK,
				resultPointCallback);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(handlerInterface, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
