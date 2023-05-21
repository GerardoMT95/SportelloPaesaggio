import { CanAccessDirective } from './can-access.directive';

describe('CanAccessDirective', () => {
  it('should create an instance', () => {
    const directive = new CanAccessDirective(null,null,null);
    expect(directive).toBeTruthy();
  });
});
