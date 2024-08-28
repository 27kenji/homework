
import lib.CoreTestCase;
import lib.CoreTestCase2;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;


public class Ex5 extends CoreTestCase2 {
    private static final String name_of_folder = "Learning programming";
    private static final String login = "27kenji", password = "2010_ats";

    @Test
    public void testSaveAndCompareTwoArticles() {

        OnboardingPageObject OnboardingPageObject = new OnboardingPageObject(driver);
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        //add first article to my list
        OnboardingPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject.waitForTitleElementAndReplace("Java (programming language)");

        String first_article_title = ArticlePageObject.getArticleTitleAndReplace("Java (programming language)");
        ArticlePageObject.addArticleToMyList(name_of_folder);

        if(Platform.getInstance().isMw()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();
            assertEquals("We are not on the same page", first_article_title, ArticlePageObject.getArticleTitle());
        }
        ArticlePageObject.closeArticle();

        //add second article to my list
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("High-level programming language");
        ArticlePageObject.waitForTitleElementAndReplace("JavaScript");
        ArticlePageObject.secondAddArticleToMyList(name_of_folder);
        ArticlePageObject.waitForTitleElement();
        /*assertEquals("We are not on the same page", second_article_title, ArticlePageObject.getArticleTitle());*/
        ArticlePageObject.closeArticle();

        //delete first article from my list
        NavigationUI.openNavigation();
        NavigationUI.closeSearchResults();
        NavigationUI.clickSavedOnMenu();
        NavigationUI.clickMyLists();
        NavigationUI.clickSavedOnMenu();
        MyListsPageObject.openFolderByName(name_of_folder);
        String deleted_article = MyListsPageObject.getArticleTitleAndReplaceWatchlist("Java (programming language)");
        MyListsPageObject.swipeByArticleToDelete(deleted_article);

        //check "Unwatch" button of the second article after delete the first article
        String saved_article = MyListsPageObject.getArticleTitleAndReplaceWatchlist("JavaScript");
        Boolean result = MyListsPageObject.assertUnwatchButtonPresent(saved_article);
        assertTrue("Unwatch button of the second article is not present after delete the first article", result);
    }


/*
    @Test
    public void testSaveAndCompareTwoArticles() {

        OnboardingPageObject OnboardingPageObject = new OnboardingPageObject(driver);
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);


        //add first article to my list
        OnboardingPageObject.skipOnboarding();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        ArticlePageObject.waitForTitleElementAndReplace("Java (programming language)");

        String article_title = ArticlePageObject.getArticleTitleAndReplace("Java (programming language)");

        ArticlePageObject.addArticleToMyList(name_of_folder);

        if(Platform.getInstance().isMw()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();
            assertEquals("We are not on the same page", article_title, ArticlePageObject.getArticleTitle());
        }
        ArticlePageObject.closeArticle();

        //add second article to my list
        SearchPageObject.clickByArticleWithSubstring("High-level programming language");
        ArticlePageObject.waitForTitleElementAndReplace("High-level programming language");
        ArticlePageObject.secondAddArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        //delete first article from my list
        NavigationUI.closeSearchResults();
        NavigationUI.clickSavedOnMenu();
        MyListsPageObject.openFolderByName(name_of_folder);
        String article_title_first = ArticlePageObject.getArticleTitleAndReplace("Java (programming language)");
        MyListsPageObject.swipeByArticleToDelete(article_title_first);

        //check title and subtitle of second article
        String title_after_delete = ArticlePageObject.getArticleTitleAndReplace("JavaScript");
        SearchPageObject.clickByArticleWithSubstring("JavaScript)");
        String substring_after_delete = ArticlePageObject.getArticleTitleAndReplace("High-level programming language");
        assertEquals("Titles do not match", title_after_delete, substring_after_delete);

    } */






        /*skipOnboarding(By.id("org.wikipedia:id/fragment_onboarding_skip_button"), "Cannot find 'Skip' button", 5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find article title", 10);

        waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Cannot find button to save article", 5);

        waitForElementAndClick(By.xpath("//android.widget.FrameLayout//*[@resource-id='org.wikipedia:id/snackbar_action']"),
                "Cannot find 'Add to list' button", 5);

        String name_of_folder = "Learning programming";
        waitForElementAndSendKeys(By.id("org.wikipedia:id/text_input"), name_of_folder,
                "Cannot put text into articles folder input", 5);

        waitForElementAndClick(By.xpath("//*[@text='OK']"), "Cannot press OK button", 5);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article", 5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='High-level programming language']"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementPresent(By.xpath("//*[@text='JavaScript']"),
                "Cannot find article title", 10);

        waitForElementAndClick(By.id("org.wikipedia:id/page_save"), "Cannot find button to save article", 5);

        waitForElementAndClick(By.xpath("//android.widget.FrameLayout//*[@resource-id='org.wikipedia:id/snackbar_action']"),
                "Cannot find 'Add to list' button", 5);

        waitForElementAndClick(By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find saved list", 5);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article", 5);

        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close search result", 5);

        waitForElementAndClick(By.xpath("//android.widget.FrameLayout[@content-desc='Saved']"),
                "Cannot find navigation button to 'Saved'", 5);

        waitForElementAndClick(By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find saved list", 5);

        swipeElementToLeft(By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java (programming language)']"),
                "Cannot find saved article");
--------
        String title_after_delete = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='JavaScript']"),
                "text",
                "Cannot find article title",
                10);

        waitForElementAndClick(By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='JavaScript']"),
                "Cannot find article title", 10);

        String bodyTitle_after_delete = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='JavaScript']"),
                "text",
                "Cannot find article title",
                10);

        Assert.assertEquals("Titles do not match", title_after_delete, bodyTitle_after_delete);

    }



    //methods

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeout) {
        WebElement element = waitForElementPresent(by, error_message, timeout);
        return element.getAttribute(attribute);
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(left_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .moveTo(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .release()
                .perform();
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    public WebElement skipOnboarding(By by, String error_message, long timeoutInSeconds) {
        WebElement elementSkip = waitForElementPresent(by, error_message, timeoutInSeconds);
        elementSkip.click();
        return elementSkip;
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element =  waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }*/


}
