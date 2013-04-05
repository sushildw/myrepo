import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class Hbase {

	/**
	 * @param args
	 */
	public void retrievePost() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.addResource(new Path("/usr/lib/hbase/conf/hbase-site.xml"));

		String[] colName = { "author", "body", "title" };
		String result = colName[0];
		HTable table = new HTable(conf, "blogposts");
		byte[] rowKey = Bytes.toBytes("post1");
		Get getRowData = new Get(rowKey);
		Result res = table.get(getRowData);
		for (int j = 0; j < colName.length; j++) {
			byte[] obtainedRow = res.getValue(Bytes.toBytes("post"),
					Bytes.toBytes(colName[j]));
			System.out.println(colName[j]);
			String s = Bytes.toString(obtainedRow);
			if (j == 0)
				result = colName[j] + "=" + s;
			else
				result = result + "&" + colName[j] + "=" + s;
			System.out.println(s);
		}

	}

	public static void main(String[] args) throws IOException {
		Hbase hb = new Hbase();
		String[] colFamilyNames = { "post" };
		String[][] colNames = { { "author", "body", "title" } };
		String[][] data = { { "sushil", "hello", "new" } };
		hb.addRecord("blogposts", colFamilyNames, colNames, data);
		hb.retrievePost();

	}

	public void addRecord(String tableName, String[] colFamilyName,
			String[][] colName, String[][] data) {
		try {
			Configuration conf = HBaseConfiguration.create();
			conf.addResource(new Path("/usr/lib/hbase/conf/hbase-site.xml"));
			HTable table = new HTable(conf, tableName);
			String row = "row" + Math.random();
			System.out.println("Row is >>>>>>>>>>>>>" + row);
			byte[] rowKey = Bytes.toBytes(row);
			Put putdata = new Put(rowKey);
			for (int j = 0; j < colFamilyName.length; j++) {
				if (colName[j].length == data[j].length) {
					for (int i = 0; i < colName[j].length; i++)
						putdata.add(Bytes.toBytes(colFamilyName[j]),
								Bytes.toBytes(colName[j][i]),
								Bytes.toBytes(data[j][i]));
				}
			}
			table.put(putdata);

		} catch (IOException e) {
			System.out.println("Exception occured in adding data");
		}
	}

}
