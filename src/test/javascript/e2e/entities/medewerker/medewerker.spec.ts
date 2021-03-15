import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MedewerkerComponentsPage, MedewerkerDeleteDialog, MedewerkerUpdatePage } from './medewerker.page-object';

const expect = chai.expect;

describe('Medewerker e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let medewerkerComponentsPage: MedewerkerComponentsPage;
  let medewerkerUpdatePage: MedewerkerUpdatePage;
  let medewerkerDeleteDialog: MedewerkerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Medewerkers', async () => {
    await navBarPage.goToEntity('medewerker');
    medewerkerComponentsPage = new MedewerkerComponentsPage();
    await browser.wait(ec.visibilityOf(medewerkerComponentsPage.title), 5000);
    expect(await medewerkerComponentsPage.getTitle()).to.eq('jhipsterWlsApp.medewerker.home.title');
    await browser.wait(ec.or(ec.visibilityOf(medewerkerComponentsPage.entities), ec.visibilityOf(medewerkerComponentsPage.noResult)), 1000);
  });

  it('should load create Medewerker page', async () => {
    await medewerkerComponentsPage.clickOnCreateButton();
    medewerkerUpdatePage = new MedewerkerUpdatePage();
    expect(await medewerkerUpdatePage.getPageTitle()).to.eq('jhipsterWlsApp.medewerker.home.createOrEditLabel');
    await medewerkerUpdatePage.cancel();
  });

  it('should create and save Medewerkers', async () => {
    const nbButtonsBeforeCreate = await medewerkerComponentsPage.countDeleteButtons();

    await medewerkerComponentsPage.clickOnCreateButton();

    await promise.all([medewerkerUpdatePage.setNaamInput('naam'), medewerkerUpdatePage.setFunctieInput('functie')]);

    expect(await medewerkerUpdatePage.getNaamInput()).to.eq('naam', 'Expected Naam value to be equals to naam');
    expect(await medewerkerUpdatePage.getFunctieInput()).to.eq('functie', 'Expected Functie value to be equals to functie');

    await medewerkerUpdatePage.save();
    expect(await medewerkerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await medewerkerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Medewerker', async () => {
    const nbButtonsBeforeDelete = await medewerkerComponentsPage.countDeleteButtons();
    await medewerkerComponentsPage.clickOnLastDeleteButton();

    medewerkerDeleteDialog = new MedewerkerDeleteDialog();
    expect(await medewerkerDeleteDialog.getDialogTitle()).to.eq('jhipsterWlsApp.medewerker.delete.question');
    await medewerkerDeleteDialog.clickOnConfirmButton();

    expect(await medewerkerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
