import { stock } from './stock.model';

describe('Tutorial', () => {
  it('should create an instance', () => {
    expect(new stock()).toBeTruthy();
  });
});
