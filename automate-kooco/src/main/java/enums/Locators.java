package enums;

public enum Locators {

	USER_NAME(".uni-input-input"),
	PASSWORD("input[type='password']"),
	LOGIN_BTN(".container .btn"),
	POPUP_CONTENT(".notice-box .content"),
	FIRST_POPUP(".notice-box .close"),
	START_TO_GRAB_ORDER_BTN(".container .btn"),
	LEVEL_ONE(".pinduoduo .pinduoduoBoxVip"),
	LEVEL_TWO(".douyin .douyinBoxVip"),
	LEVEL_THREE(".weipinghui .weipinghuiBoxVip"),
	LEVEL_FOUR(".tianmao .tianmaoBoxVip"),
	LEVEL_FIVE(".AmazonBox .AmazonBoxVip"),
	KOOCO_LOGO("uni-view.box"),
	SUBMIT_BTN("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(4) > uni-view > uni-button"),
	FROZEN_ORDERS("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view.body.h5 > uni-view.top > uni-view:nth-child(3) > uni-view.right > uni-view.value"),
	TOTAL_ORDERS_GRABBED_TODAY("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view.body.h5 > uni-view.top > uni-view:nth-child(3) > uni-view.left > uni-view.value"),
	CURRENT_BALANCE("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view.body.h5 > uni-view.top.flex-row > uni-view:nth-child(1) > uni-view.value > uni-text > span"),
	AVAILABLE_FOR_WITHDRAWAL("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view.body.h5 > uni-view.top.flex-row > uni-view:nth-child(2) > uni-view.value > uni-text > span"),
	TODAY_REVENUE("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(2) > uni-view > uni-view.body > uni-view:nth-child(2) > uni-view.top1.flex-row > uni-view.left > uni-view.value"),
	TOTAL_REVENUE("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(2) > uni-view > uni-view.body > uni-view:nth-child(2) > uni-view.top1.flex-row > uni-view.right > uni-view.value"),
	ORDER_PROFIT_TODAY("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(2) > uni-view > uni-view.body > uni-view:nth-child(2) > uni-view.bottom.flex-row > uni-view.left > uni-view.value"),
	TOTAL_MONEY("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(2) > uni-view > uni-view.body > uni-view:nth-child(2) > uni-view.bottom.flex-row > uni-view.right > uni-view.value"),
	TODAY_TEAM_COMMISSION("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(2) > uni-view > uni-view.body > uni-view:nth-child(4) > uni-view.bottom.flex-row > uni-view.left > uni-view.value"),
	TOTAL_TEAM_COMMISSION("body > uni-app > uni-page > uni-page-wrapper > uni-page-body > uni-view > uni-view:nth-child(2) > uni-view > uni-view.body > uni-view:nth-child(4) > uni-view.bottom.flex-row > uni-view.right > uni-view.value");
	
	private String locator;
	
	private Locators(String locator) {
		this.locator = locator;
	}
	
	@Override
	public String toString() {
		return locator;
	}

}

