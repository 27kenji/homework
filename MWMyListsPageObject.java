package lib.ui.mobile_web;

import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListsPageObject extends MyListsPageObject {
    static {
        ARTICLE_BY_TITLE_TPL = "xpath://ul[contains(@class, 'mw-mf-watchlist-page-list')]//h3[contains(text(), '{TITLE}')]";
        /*REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class, 'content-unstyled mw-mf-page-list thumbs page-summary-list mw-mf-watchlist-page-list')]//h3[contains(text(), '{TITLE}')]//..//..//span[contains(text(), 'Unwatch')]";*/
        REMOVE_FROM_SAVED_BUTTON = "xpath://ul[contains(@class, 'content-unstyled mw-mf-page-list thumbs page-summary-list mw-mf-watchlist-page-list')]//li[contains(@title, '{TITLE}')]//span[contains(@class, 'mf-icon mf-icon-unStar-progressive')]";
        TITLE_REPLACE_WATCHLIST = "xpath://h3[contains(text(), '{SUBSTRING}')]";

    }

    public MWMyListsPageObject(RemoteWebDriver driver) {
        super(driver);

    }
}
