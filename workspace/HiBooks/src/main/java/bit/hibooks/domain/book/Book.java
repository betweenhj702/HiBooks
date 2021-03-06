package bit.hibooks.domain.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
	private long b_seq;
	private String b_itemId;
	private String b_title;
	private String b_img;
	private double b_rate;
	private String b_writer;
	private String b_translator;
	private String b_publisher;
	private long b_price;
	private String b_desc;
	private int b_cate;
	private String b_cate2;
	private String b_keyword;
}
