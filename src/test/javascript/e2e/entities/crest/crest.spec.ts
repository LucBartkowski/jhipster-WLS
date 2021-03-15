import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CrestComponentsPage, CrestDeleteDialog, CrestUpdatePage } from './crest.page-object';

const expect = chai.expect;

describe('Crest e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let crestComponentsPage: CrestComponentsPage;
  let crestUpdatePage: CrestUpdatePage;
  let crestDeleteDialog: CrestDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Crests', async () => {
    await navBarPage.goToEntity('crest');
    crestComponentsPage = new CrestComponentsPage();
    await browser.wait(ec.visibilityOf(crestComponentsPage.title), 5000);
    expect(await crestComponentsPage.getTitle()).to.eq('jhipsterWlsApp.crest.home.title');
    await browser.wait(ec.or(ec.visibilityOf(crestComponentsPage.entities), ec.visibilityOf(crestComponentsPage.noResult)), 1000);
  });

  it('should load create Crest page', async () => {
    await crestComponentsPage.clickOnCreateButton();
    crestUpdatePage = new CrestUpdatePage();
    expect(await crestUpdatePage.getPageTitle()).to.eq('jhipsterWlsApp.crest.home.createOrEditLabel');
    await crestUpdatePage.cancel();
  });

  it('should create and save Crests', async () => {
    const nbButtonsBeforeCreate = await crestComponentsPage.countDeleteButtons();

    await crestComponentsPage.clickOnCreateButton();

    await promise.all([
      crestUpdatePage.setVerantwoordelijkeCrestInput('verantwoordelijkeCrest'),
      crestUpdatePage.setNaamEntiteitInput('naamEntiteit'),
    ]);

    expect(await crestUpdatePage.getVerantwoordelijkeCrestInput()).to.eq(
      'verantwoordelijkeCrest',
      'Expected VerantwoordelijkeCrest value to be equals to verantwoordelijkeCrest'
    );
    expect(await crestUpdatePage.getNaamEntiteitInput()).to.eq('naamEntiteit', 'Expected NaamEntiteit value to be equals to naamEntiteit');

    await crestUpdatePage.save();
    expect(await crestUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await crestComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Crest', async () => {
    const nbButtonsBeforeDelete = await crestComponentsPage.countDeleteButtons();
    await crestComponentsPage.clickOnLastDeleteButton();

    crestDeleteDialog = new CrestDeleteDialog();
    expect(await crestDeleteDialog.getDialogTitle()).to.eq('jhipsterWlsApp.crest.delete.question');
    await crestDeleteDialog.clickOnConfirmButton();

    expect(await crestComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
