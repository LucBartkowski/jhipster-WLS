import { element, by, ElementFinder } from 'protractor';

export class MedewerkerComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-medewerker div table .btn-danger'));
  title = element.all(by.css('jhi-medewerker div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class MedewerkerUpdatePage {
  pageTitle = element(by.id('jhi-medewerker-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  naamInput = element(by.id('field_naam'));
  functieInput = element(by.id('field_functie'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNaamInput(naam: string): Promise<void> {
    await this.naamInput.sendKeys(naam);
  }

  async getNaamInput(): Promise<string> {
    return await this.naamInput.getAttribute('value');
  }

  async setFunctieInput(functie: string): Promise<void> {
    await this.functieInput.sendKeys(functie);
  }

  async getFunctieInput(): Promise<string> {
    return await this.functieInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class MedewerkerDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-medewerker-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-medewerker'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
