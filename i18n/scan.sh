# all code translations
find .. -name '*.java' | xargs xgettext --from-code=UTF-8 -L Java --force-po -kI18n.register -kI18n.t:1,1t -kI18n.t:1,2,3t -kI18n.t:1,2c,2t -kI18n.t:1,2,3c,4t --sort-output --no-wrap -o ./po/keys.pot

# update translation files with new content
msgmerge --no-wrap --sort-output --backup=none --no-fuzzy-matching --lang=nl -U ./po/i18n_nl.po ./po/keys.pot
msgmerge --no-wrap --sort-output --backup=none --no-fuzzy-matching --lang=en -U ./po/i18n_en.po ./po/keys.pot

