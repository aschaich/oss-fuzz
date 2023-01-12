// Copyright 2022 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////


package io.netty.buffer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.CharBuffer;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;

public class ByteBufStreamFuzzer {
	
	private FuzzedDataProvider fuzzedDataProvider;
	
	public ByteBufStreamFuzzer(FuzzedDataProvider fuzzedDataProvider) {
		this.fuzzedDataProvider = fuzzedDataProvider;
	}

	public String getFuzzyString() {
		int length = fuzzedDataProvider.consumeInt(0, fuzzedDataProvider.remainingBytes());
		return fuzzedDataProvider.consumeString(length);
	}

	void test() {

	
		ByteBuf buf = Unpooled.buffer(0, 65536);
		ByteBufOutputStream out = new ByteBufOutputStream(buf);

		try {
			out.writeBoolean(fuzzedDataProvider.consumeBoolean());
			out.writeDouble(fuzzedDataProvider.consumeDouble());
			out.writeUTF(getFuzzyString());

			out.writtenBytes();
			out.close();
		} catch (IllegalArgumentException e) {

		} catch (IOException e) {
			
		}

		
	}

	public static void fuzzerTestOneInput(FuzzedDataProvider fuzzedDataProvider) {
		ByteBufStreamFuzzer fixture = new ByteBufStreamFuzzer(fuzzedDataProvider);
		fixture.test();
	}
}