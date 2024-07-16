package folders

import javaposse.jobdsl.dsl.DslFactory

class WebFolder {

  public WebFolder(DslFactory factory) {
    
    factory.folder('web') {
      displayName('Web')
    }
    factory.folder('web/build'){
      displayName('Backend')
    }
    factory.folder('web/deploy'){
      displayName('Frontend')
    }

  }
}