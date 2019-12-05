
  Pod::Spec.new do |s|
    s.name = 'CapacitorAdmob'
    s.version = '0.0.1'
    s.summary = 'This is Ionic Capacitor native AdMob plugin for IOS & Android'
    s.license = 'MIT'
    s.homepage = 'https://github.com/rahadur/capacitor-admob'
    s.author = 'Rahadur Rahman <get.rahadur@gmail.com>'
    s.source = { :git => 'https://github.com/rahadur/capacitor-admob', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.static_framework = true
    s.dependency 'Capacitor'
    s.dependency 'Google-Mobile-Ads-SDK'
  end
