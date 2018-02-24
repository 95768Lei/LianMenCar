package com.zl.webproject.pinyin;

import com.zl.webproject.model.CityBean;
import com.zl.webproject.model.Friend;

import java.util.Comparator;

/**
 * 
 * @author 
 *
 */
public class PinyinComparator implements Comparator<CityBean> {


	public static PinyinComparator instance = null;

	public static PinyinComparator getInstance() {
		if (instance == null) {
			instance = new PinyinComparator();
		}
		return instance;
	}

	public int compare(CityBean o1, CityBean o2) {
		if (o1.getLetters().equals("@")
				|| o2.getLetters().equals("#")) {
			return -1;
		} else if (o1.getLetters().equals("#")
				|| o2.getLetters().equals("@")) {
			return 1;
		} else {
			return o1.getLetters().compareTo(o2.getLetters());
		}
	}

}
