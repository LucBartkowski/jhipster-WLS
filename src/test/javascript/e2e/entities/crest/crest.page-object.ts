import { element, by, ElementFinder } from 'protractor';

export class CrestComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-crest div table .btn-danger'));
  title = element.all(by.css('jhi-crest div h2#page-heading span')).first();
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

export class CrestUpdatePage {
  pageTitle = element(by.id('jhi-crest-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  verantwoordelijkeCrestInput = element(by.id('field_verantwoordelijkeCrest'));
  naamEntiteitInput = element(by.id('field_naamEntiteit'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVerantwoordelijkeCrestInput(verantwoordelijkeCrest: string): Promise<void> {
    await this.verantwoordelijkeCrestInput.sendKeys(verantwoordelijkeCrest);
  }

  async getVerantwoordelijkeCrestInput(): Promise<string> {
    return await this.verantwoordelijkeCrestInput.getAttribute('value');
  }

  async setNaamEntiteitInput(naamEntiteit: string): Promise<void> {
    await this.naamEntiteitInput.sendKeys(naamEntiteit);
  }

  async getNaamEntiteitInput(): Promise<string> {
    return await this.naamEntiteitInput.getAttribute('value');
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

export class CrestDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-crest-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-crest'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
