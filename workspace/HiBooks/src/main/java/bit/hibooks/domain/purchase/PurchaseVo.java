package bit.hibooks.domain.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseVo {
	private String m_email;
	private String s_name;
	private String s_addr;
	private String s_phone;
	private String s_msg;
	private String s_zipNo;
	private String o_shipno;
	private long o_paidAmount;
	private String o_paytype;
}
