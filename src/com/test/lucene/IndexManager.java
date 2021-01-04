package com.test.lucene;

import java.io.File;
import java.util.ArrayList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexManager {
	public static void main(String[] args) {
		IndexManager indexMng = new IndexManager();
		indexMng.doIndex();
	}
	
	/**
	 * 색인하기
	 */
	public void doIndex() {
		
		// 색인 결과파일 폴더 지정
		String indexDir = "D:\\index";
		
		// 색인할 txt 파일들이 있는 대상 폴더 지정
		String dataDir = "D:\\search\\folder";
		
		Directory dir = null;
		IndexWriterConfig config = null;
		IndexWriter indexWriter = null;
		
		String[] fileContent = {"찾아봐검색어 루씬이란 무엇인가? 에서 부터 시작을 해봐야 할 것 같습니다.",
				"찾아봐검색어 라이브러리를 제공합니다.",
				"찾아봐검색어 검색엔진이라고 하면",
				"찾아봐검색어 키워드분석 / 색인 / 검색의 과정을 수행하는 것으로",
				"찾아봐검색어 크게 나눠 볼 수 있겠는데, 이런 과정들을 실제로 구현 할 수 있는",
				"찾아봐검색어 API를 제공합니다..",
				"찾아봐검색어 색인 예제는 파일로부터 파일의 내용을 읽어서",
				"찾아봐검색어 이를 색인하는 예제입니다. 파일 그 자체를 Stream으로 읽어들이기 때문에",
				"찾아봐검색어 가급적이면 색인 대상 파일들이 txt파일등 일반 텍스트 문서로 되어 있으면 나중에",
				"찾아봐검색어 SearchFiles 데모에서 확인이 쉬울 것 입니다."};
		
		try {
			dir = FSDirectory.open(new File(indexDir).toPath());
			config = new IndexWriterConfig(new StandardAnalyzer());
			indexWriter = new IndexWriter(dir, config);
			
//			File[] fileArr = new File(dataDir).listFiles();
//			int fileCount = fileArr.length;
			
			ArrayList<String> fileList = new ArrayList<String>();
			FileReadUtil.addAllTextFiles(fileList, new File(dataDir));
			//int fileCount = fileList.size();
			int stringCnt = fileContent.length;
			
			//System.out.println("파일개수 : " + fileCount + "개");
			System.out.println("파일개수 : " + stringCnt + "개");
			
			File file = null;
			//for (int i=0; i<fileCount; i++) {
			for (int i=0; i<stringCnt; i++) {
				// file = fileArr[i];
				/*file = new File(fileList.get(i));
				
				if (file.isDirectory()) {
					continue;
				}
				
				if (file.isHidden()) {
					continue;
				}
				
				if (!file.exists()) {
					continue;
				}
				
				if (!file.canRead()) {
					continue;
				}
				
				if (file.getName().toLowerCase().endsWith(".txt")) {
					indexFile(indexWriter, file);
				}
				if (file.getName().toLowerCase().endsWith(".exsl")) {
					indexFile(indexWriter, file);
				}
				if (file.getName().toLowerCase().endsWith(".csv")) {
					indexFile(indexWriter, file);
				}*/
				indexFile(indexWriter, fileContent[i]);
			}
			
			System.out.println(indexWriter.numRamDocs() + "개 파일 색인처리 완료.");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				if (indexWriter != null) {
					indexWriter.close();
				}
			} catch (Exception e) {
				indexWriter = null;
			}
			
			try {
				if (dir != null) {
					dir.close();
				}
			} catch (Exception e) {
				dir = null;
			}
		}
	}

	
	//public void indexFile(IndexWriter indexWriter, File file) throws Exception {
	public void indexFile(IndexWriter indexWriter, String fileCont) throws Exception {
		//Document doc = getDocument(file);
		Document doc = getDocument(fileCont);
		indexWriter.addDocument(doc);
	}
	
	
	//private Document getDocument(File file) throws Exception {
	private Document getDocument(String fileCont) throws Exception {
		//String fileContent = FileReadUtil.readFileToString(file, "MS949", " ");
		/*String fileContent = "루씬이란 무엇인가? 에서 부터 시작을 해봐야 할 것 같습니다.\r\n" + 
				"루씬은 full text 검색엔진을 만들 수 있는\r\n" + 
				"라이브러리를 제공합니다.\r\n" + 
				"\r\n" + 
				"검색엔진이라고 하면\r\n" + 
				"키워드분석 / 색인 / 검색의 과정을 수행하는 것으로\r\n" + 
				"크게 나눠 볼 수 있겠는데, 이런 과정들을 실제로 구현 할 수 있는\r\n" + 
				"API를 제공합니다.\r\n" + 
				"\r\n" + 
				"우선 루씬을 사용해서\r\n" + 
				"색인과 검색이 어떠한 방식으로 이루어지는지\r\n" + 
				"예제를 보는 것이 가장 손쉽게 루씬에 대해서 알아 볼 수 있는 방법이 \r\n" + 
				"될 것 같습니다.\r\n" + 
				"\r\n" + 
				"이 예제들은 파일들로부터 내용을 색인하고\r\n" + 
				"그 색인 파일을 사용하여 키워드로 내용을 검색하는 예제입니다.\r\n" + 
				"\r\n" + 
				"굉장히 간단한 예제이지만\r\n" + 
				"사실 검색엔진을 구현하는데 있어서 가장 필수적인\r\n" + 
				"내용들은 거의 다 들어가 있다고 보셔도 됩니다.\r\n" + 
				"\r\n" + 
				"그만큼 이 루씬이라는 라이브러리가 잘 만들어져 있다는\r\n" + 
				"이야기도 되겠네요. 물론 스코어의 세부적인 컨트롤, 리플리케이션, 하이라이트등의\r\n" + 
				"고급 기능은 포함되어 있지 않습니다.\r\n" + 
				"\r\n" + 
				"사실 이번에 소개해드릴 데모 프로그램은 루씬 패키지를 다운 받으면 \r\n" + 
				"보실 수 있는 데모 프로그램입니다. \r\n" + 
				"이 프로그램을 불필요한 내용들은  제거하여 다시 보여드리려고 합니다.\r\n" + 
				"\r\n" + 
				"원 소스에 있던 주석들에도 좋은 이야기가 많기 때문에\r\n" + 
				"원 주석은 그대로 두고\r\n" + 
				"몇몇 부분의 주석을 더 하고 Line 단위로 \r\n" + 
				"설명을 조금씩 붙여보려고 합니다.\r\n" + 
				"\r\n" + 
				"간단하게 공유 할 용어들을 확인해보겠습니다.\r\n" + 
				"\r\n" + 
				"색인과 검색은 다 아실거라 생각하고 색인의 대상이 되는 RawData..\r\n" + 
				"이 RawData를 일반적으로 문서(Document)라고 부릅니다.\r\n" + 
				"아마도, 검색이라는 것이 문서를 찾기 위해 발전된 기술이라서 그런 것이 아닐까 싶네요.\r\n" + 
				"\r\n" + 
				"루씬에서도 색인을 하기 위해서 사용되는 클래스가 Document라는 이름으로\r\n" + 
				"설계가 되어있습니다.\r\n" + 
				"\r\n" + 
				"이 Document들이 색인되어 있는 것을 인덱스파일이라고 하며, 이 인덱스 파일에서\r\n" + 
				"문서를 찾는 과정을 검색이라고 합니다. 그리고 검색된 Document들을 나열하는 것을\r\n" + 
				"Ranking(혹은 Sort)... 이 Ranking에 사용되는 점수를 Score라고 합니다.\r\n" + 
				"\r\n" + 
				"마찬가지로 루씬에서는 Sort, Score라는 이름이 사용되고 있습니다.\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"우선 색인 예제를 보시겠습니다.\r\n" + 
				"\r\n" + 
				"색인 예제는 파일로부터 파일의 내용을 읽어서\r\n" + 
				"이를 색인하는 예제입니다. 파일 그 자체를 Stream으로 읽어들이기 때문에\r\n" + 
				"가급적이면 색인 대상 파일들이 txt파일등 일반 텍스트 문서로 되어 있으면 나중에\r\n" + 
				"SearchFiles 데모에서 확인이 쉬울 것 입니다.\r\n" + 
				"\r\n" + 
				"\r\n";*/
		
		Document doc = new Document();
		//for(int i = 0; i < 10; i++) {
			doc.add(new TextField("contents", String.valueOf(fileCont), Field.Store.YES));
			//doc.add(new TextField("filename", String.valueOf(i), Field.Store.YES));
			//doc.add(new TextField("fullpath", String.valueOf(file.getCanonicalPath()), Field.Store.YES));
			//System.out.println("canonicalPath : " + file.getCanonicalPath());
			//System.out.println("filename : " + i+file.getName());
			System.out.println("fileContent : " + fileCont);
			System.out.println("====================");
		//}
		return doc;
	}
}
