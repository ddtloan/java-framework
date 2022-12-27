package actions.commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class AbstractPage {
    WebDriver driver;
    private WebElement element;
    private Select select;
    private JavascriptExecutor javascriptExecutor;
    private WebDriverWait waitExplicit;
    private List<WebElement> elements;
    private Actions action;
    private By byLocator;

    /*Web Browser*/
    public void openUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public void getCurrentUrl(WebDriver driver) {
        driver.getCurrentUrl();
    }

    public void getPageTitle(WebDriver driver) {
        driver.getTitle();
    }

    public void getPageSource(WebDriver driver) {
        driver.getPageSource();
    }

    public void backToPage(WebDriver driver) {
        driver.navigate().back();
    }

    public void forwardToPage(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refreshToPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public void cancelAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public void sendKeyToAlert(WebDriver driver, String value) {
        driver.switchTo().alert().sendKeys(value);
    }

    public String getTextAlert(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    /*Web Element*/
    public void clickToElement(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        element.click();
    }

    public void sendKeyToElement(WebElement driver, String locator, String value) {
        element = driver.findElement(By.xpath(locator));
        element.sendKeys(value);
    }

    public void selectItemInDropdown(WebElement driver, String locator, String itemText) {
        element = driver.findElement(By.xpath(locator));
        select = new Select(element);
        select.selectByVisibleText(itemText);
    }

    public String getSelectedItemInDropdown(WebElement driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }

    public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String allItemsInDropdown, String expectedText) {
        element = driver.findElement(By.xpath(parentLocator));
        javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click()", element);

        waitExplicit = new WebDriverWait(driver, Duration.ofSeconds(30));
        waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsInDropdown)));
        elements = driver.findElements(By.xpath(allItemsInDropdown));

        for (int i = 0; i < elements.size(); i++) {
            String itemText = elements.get(i).getText();
            if (itemText.equals(expectedText)) {
                javascriptExecutor.executeScript("arguments[0].scrollIntoView(true)", elements.get(i));
                javascriptExecutor.executeScript("arguments[0].click()", elements.get(i));
                break;
            }
        }
    }

    public String getAttributeValue(WebDriver driver, String locator, String attributeName) {
        element = driver.findElement(By.xpath(locator));
        return element.getAttribute(attributeName);
    }

    public String getTextElement(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        return element.getText();
    }

    public int countElementsNumber(WebDriver driver, String locator) {
        elements = driver.findElements(By.xpath(locator));
        return elements.size();

    }

    public void checkTheCheckbox(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void unCheckTheCheckbox(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isControlDisplayed(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        return element.isDisplayed();
    }

    public boolean isControlSelected(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        return element.isSelected();
    }

    public boolean isControlEnabled(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        return element.isEnabled();
    }

    public void switchToChildWindowByID(WebDriver driver, String parent) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parent)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }

    public void switchToWindowByTitle(WebDriver driver, String title) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindows : allWindows) {
            driver.switchTo().window(runWindows);
            String currentWin = driver.getTitle();
            if (currentWin.equals(title)) {
                break;
            }
        }
    }

    public boolean closeAllWindowWithoutParent(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                driver.close();
            }
        }
        driver.switchTo().window(parentID);
        if (driver.getWindowHandles().size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void doubleClickToElement(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        action = new Actions(driver);
        action.doubleClick(element).perform();
    }

    public void hoverMouseToElement(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    public void rightClick(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        action = new Actions(driver);
        action.contextClick(element).perform();
    }

    public void sendKeyboardToElement(WebDriver driver, String locator, Keys key) {
        element = driver.findElement(By.xpath(locator));
        action = new Actions(driver);
        action.sendKeys(element, key).perform();
    }

    public Object executeForBrowser(WebDriver driver, String javaScript) {
        javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript(javaScript);
    }

    public void clickToElementByJS(WebDriver driver, WebElement element) {
        javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].click()", element);
    }

    public void sendKeyToElementByJS(WebDriver driver, WebElement element, String value) {
        javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].setAttribute('value','" + value + "')", element);
    }

    public boolean checkAnnyImagesLoaderByJS(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        javascriptExecutor = (JavascriptExecutor) driver;
        return (boolean) (Boolean) (javascriptExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", element));
    }

    public Object clickToElementByJS(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript("arguments[0].click();", element);
    }

    public Object scrollToElementByJS(WebDriver driver, String locator) {
        element = driver.findElement(By.xpath(locator));
        javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public Object sendKeysToElementByJS(WebDriver driver, String locator, String value) {
        element = driver.findElement(By.xpath(locator));
        javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript("arguments[0].setAttribute(value, '" + value + "')", element);
    }

    public void scrollToBottomPageByJS(WebDriver driver) {
        javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollBy(0, document.body.scrollHeight)");
    }

    public Object removeAttributeInDOMByJS(WebDriver driver, String locator, String attributeName) {
        element = driver.findElement(By.xpath(locator));
        javascriptExecutor = (JavascriptExecutor) driver;
        return javascriptExecutor.executeScript("arguments[0].removeAttribute('" + attributeName + "')", element);
    }

    public void waitForElementPresence(WebDriver driver, String locator) {
        waitExplicit = new WebDriverWait(driver, Duration.ofSeconds(30));
        byLocator = By.xpath(locator);
        waitExplicit.until(ExpectedConditions.presenceOfElementLocated(byLocator));
    }

    public void waitForElementVisible(WebDriver driver, String locator) {
        waitExplicit = new WebDriverWait(driver, Duration.ofSeconds(30));
        byLocator = By.xpath(locator);
        waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
    }

    public void waitForElementClickAble(WebDriver driver, String locator) {
        waitExplicit = new WebDriverWait(driver, Duration.ofSeconds(30));
        byLocator = By.xpath(locator);
        waitExplicit.until(ExpectedConditions.elementToBeClickable(byLocator));
    }

    public void waitForElementInvisible(WebDriver driver, String locator) {
        waitExplicit = new WebDriverWait(driver, Duration.ofSeconds(30));
        byLocator = By.xpath(locator);
        waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(byLocator));
    }
}
