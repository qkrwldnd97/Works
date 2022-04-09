export class Vocab {
  constructor(word, definition, partofspeech, pheonics, origin, test, source, check) {
    this.word = word;
    this.definition = definition;
    this.partofspeech = partofspeech;
    this.pheonics = pheonics;
    this.origin = origin;
    this.test1 = 'white';
    this.test2 = 'white';
    this.test3 = 'white';
    if (test === 1) {
      this.test1 = 'blue';
    } else if (test === 2) {
      this.test1 = 'blue';
      this.test2 = 'blue';
    } else if (test === 3) {
      this.test1 = 'blue';
      this.test2 = 'blue';
      this.test3 = 'blue';
    } else if (test === 4){
      this.test1 = 'blue';
      this.test2 = 'blue';
      this.test3 = 'blue';
      this.test4 = 'blue';
    }
    this.source = source;
    this.check = check
  }
  setCheck = () => {
    this.check = !this.check;
  }
  
}
