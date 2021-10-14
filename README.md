# BeginnerManagement
新規プレイヤーによる荒らし行為を防止するプラグインです。
>It is a plugin that prevents vandalism by new players.

# 機能詳細 | Features

* 時間制行動制限 | Time-based behavioral restrictions

初回ログイン後、Configで設定した時間行動を制限出来ます。
>After logging in for the first time, you can limit the time action set in Config.

# 制限可能項目 | List of actions that can be restricted

* ブロック破壊 | Destruction of blocks

  全てのブロックの破壊を禁止します。
  >Destruction of all blocks is prohibited.
  
* 特定のアイテムをチェスト等から取得 | Get a specific item from a chest, etc.

  Configで設定したアイテムをチェストから取り出し/アイテムのドロップ/インベントリ内の移動を制限します。
  >Remove items set in Config from chest / drop items / restrict movement in inventory.

* 特定のブロックを設置・破壊 | Build / destroy specific blocks.

  Configで設定したブロックの建築・破壊を制限します。
  >Restricts the construction and destruction of blocks set in Config.

* 特定のエンティティ(MOB等)への攻撃 | Attack on a specific entity.

  Configで設定したエンティティへの攻撃を制限します。
  >Limits attacks on entities set in Config.

* 特定のエンティティの設置(トロッコ等) | Attack on a specific entity.

  Configで設定したエンティティへの攻撃を制限します。
  >Limits attacks on entities set in Config.

* マグマ・水バケツの使用 |Use of lava or water bucket

  マグマバケツまたは水バケツの使用を制限します。
  >Restrict the use of lava buckets or water buckets.

* 火打石・ファイヤーチャージの使用 | Attack on a specific entity.

  火打石またはファイヤーチャージでの着火を制限します。
  >Limits ignition with flint or fire charge.


# 任意プラグイン | Optional plugin

* DiscordSRV
  危険行為を行ったプレイヤーが居た場合、コンソールチャンネルにメッセージを送信します。
  >If there is a player who has done a dangerous act, a message will be sent to the console channel.

# コマンド | Commands

* /bm info

  自分の制限解除時刻を表示します。(表示内容はConfigから変更可能)
  >Displays your own restriction release time. (Display contents can be changed from Config)
* /bm info [playername]

  指定したプレイヤーの初回ログイン時刻・制限解除時刻を表示します。
  >Displays the first login time and restriction release time of the specified player.
* /bm whitelist [playername]

  指定したプレイヤーをホワイトリストに追加します。ホワイトリストのユーザーは制限解除時刻に関係無く制限が解除されます。
  >Adds the specified player to the white list. Whitelist users will be lifted regardless of the time the limit is lifted.
* /bm reload

  Configをリロードします。
  >Reload Config.
* /bm help

  コマンドガイドを表示します。
  >Display the command guide.

# Permission

* beginnerManagement.admin

  自分あるいは他人の制限状況表示が行えます。
  >You can display the restriction status of yourself or others.
* beginnerManagement.info

  /bm info コマンドが利用出来ます。
  >The / bm info command is available.
* beginnerManagement.anotherInfo

  /bm info [playername]コマンドが利用出来ます。
  >The / bm info [playername] command is available.
* beginnerManagement.whitelist

  ホワイトリストへの追加が行えます。
  >You can add to the white list.
* beginnerManagement.reload

  /bm reload コマンドが利用出来ます。
  >The / bm reload command is available.
* beginnerManagement.allow

  制限時間に関わらず制限をスルーします。特定のワールドでのみ制限を行いたくない場合などにご利用下さい。
  >Through the limit regardless of the time limit. Please use it when you do not want to limit only in a specific world.
