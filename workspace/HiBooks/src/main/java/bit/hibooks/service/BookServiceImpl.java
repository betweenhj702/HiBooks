package bit.hibooks.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import bit.hibooks.domain.book.Book;
import bit.hibooks.domain.book.BookListResult;
import bit.hibooks.domain.book.BookReview;
import bit.hibooks.domain.book.BookVo;
import bit.hibooks.domain.book.ContentVo;
import bit.hibooks.domain.purchase.WishVo;
import bit.hibooks.domain.review.ReviewResult;
import bit.hibooks.domain.review.ReviewVo;
import bit.hibooks.mapper.BookMapper;
import bit.hibooks.mapper.WishListMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
	
	private BookMapper mapper;
	private WishListMapper mapperWish;
	
	@Override
	public BookListResult getList(BookVo bookVo) {
		//log.info(bookVo.getCp() +" "+ bookVo.getPs());
		
		List<Book> bookList = mapper.getBookList(bookVo);
		long totalCnt = mapper.getTotalCnt();
		long novelCnt = mapper.getNovelCnt();
		long economyCnt = mapper.getEconomyCnt();
		long adviceCnt = mapper.getAdviceCnt();
		long humanCnt = mapper.getHumanCnt();
		long poemCnt = mapper.getPoemCnt();
		
		return new BookListResult(totalCnt, novelCnt, economyCnt, adviceCnt, 
					humanCnt, poemCnt, bookVo.getCp(), bookVo.getPs() , bookList, bookVo.getCate() );
	}

	@Override
	public Book getBook(String itemId) {
		return mapper.getBook(itemId);
	}
	
	@Override
	public ReviewResult getReviewList(ReviewVo reviewVo) {
		long reviewCnt = mapper.getReviewCnt(reviewVo);
		List<BookReview> reviewList = mapper.getReviewList(reviewVo);
		ReviewResult reviewResult = new ReviewResult(reviewVo.getRcp(), reviewVo.getRps(), reviewCnt, reviewList);
		return reviewResult;
	}
	
	@Override
	public ReviewResult writeReview(BookReview br, ReviewVo reviewVo) throws DataAccessException{
		br.setBr_ref(mapper.getMaxRef() + 1);
		mapper.insertReview(br);
		
		return getReviewList(reviewVo);
	}

	@Override
	public ReviewResult deleteReview(BookReview br, ReviewVo reviewVo) {
		mapper.deleteReview(br);
		reviewVo.setItemId(br.getB_itemId());
		return getReviewList(reviewVo);
	}

	@Override
	public ReviewResult updateReview(BookReview br, ReviewVo reviewVo) {
		mapper.updateReview(br);
		return getReviewList(reviewVo);
	}

	@Override
	public ReviewResult likeReview(BookReview br, ReviewVo reviewVo) {
		try {
			mapper.insertRecommend(br);
			mapper.updateLike(br);
			return getReviewList(reviewVo);
		}catch(DataAccessException dae) {
			return null;
		}
	}

	@Override
	public String isBookInWish(WishVo wishVo) {
		int cnt = mapperWish.isBookInWishList(wishVo);
		if(cnt == 0) {
			return "unselected";
		}else return "selected";
	}
	
	@Override
	public List<Book> getRecommendedBook(String itemId) {
		Book book = mapper.getBook(itemId);
		int b_cate = book.getB_cate();
		String keyword = book.getB_keyword();
		String key[] = keyword.split(",");
		ContentVo contentVo = new ContentVo(itemId, b_cate, key[5],key[6],key[7],key[8],key[9], 8, null);
		return mapper.selectRecommendList(contentVo);
	}
	
	@Override
	public List<Book> getWriterBook(String itemId){
		Book book = mapper.getBook(itemId);
		String b_writer = book.getB_writer();
		ContentVo contentVo = new ContentVo();
		contentVo.setB_writer(b_writer);
		contentVo.setB_itemId(itemId);
		return mapper.selectWriterList(contentVo);
	}
}
