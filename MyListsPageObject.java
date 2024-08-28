package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
    FOLDER_BY_NAME_TPL,
    ARTICLE_BY_TITLE_TPL,
    TITLE_REPLACE_WATCHLIST,
    ADD_TO_SAVED_BUTTON,
    REMOVE_FROM_SAVED_BUTTON;


    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);

    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);

    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);

    }

    private static String getWatchlistTitle(String substring) {
        return TITLE_REPLACE_WATCHLIST.replace("{SUBSTRING}", substring);
    }

    /*private static String getAddButtonByTitle(String substring) {
        return ADD_TO_SAVED_BUTTON.replace("{SUBSTRING}", substring);
    }*/

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        if(Platform.getInstance().isAndroid()) {
            String folder_name_xpath = getFolderXpathByName(name_of_folder);
            this.waitForElementAndClick(folder_name_xpath,
                    "Cannot find folder by name " + name_of_folder, 5);
        } else {
                System.out.println("Method closeArticle does nothing for platform");
        }

    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath, "Cannot find saved article by title " + article_title, 10);

    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        if(Platform.getInstance().isAndroid()) {
            String article_xpath = getSavedArticleXpathByTitle(article_title);
            this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + article_title, 10);
        } else {
            System.out.println("Method closeArticle does nothing for platform");
        }

    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        if(Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(article_xpath,
                    "Cannot find saved article");
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(remove_locator, "Cannot click button to remove article from saved", 10);

        }
        if(Platform.getInstance().isMw()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(article_title);
    }


        public String getArticleTitleAndReplaceWatchlist(String substring) {
        WebElement title_element_watchlist = waitForTitleElementAndReplaceWatchlist(substring);
        if(Platform.getInstance().isAndroid()) {
            return title_element_watchlist.getAttribute("text");
        } else if (Platform.getInstance().isMw()) {
            return title_element_watchlist.getText();
        } else {
            return title_element_watchlist.getAttribute("text");
        }
    }

        public WebElement waitForTitleElementAndReplaceWatchlist(String substring) {
        String search_result_button = getWatchlistTitle(substring);
        return this.waitForElementPresent(search_result_button, "Cannot find title with substring " + substring, 10);

    }

    public WebElement waitForWatchElementAndReplaceWatchlist(String substring) {
        String search_result_button = getRemoveButtonByTitle(substring);
        return this.waitForElementPresent(search_result_button, "Cannot find title with substring " + substring, 10);

    }

    public boolean assertUnwatchButtonPresent(String locator) {
        WebElement search_result_button = waitForWatchElementAndReplaceWatchlist(locator);
        Boolean button = search_result_button.isDisplayed();
        if (button == false) {
            String default_message = "A button Unwatch is not present";
            throw new AssertionError(default_message);
        } return button;
    }



}
