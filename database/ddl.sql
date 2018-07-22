facilities:
CREATE TABLE IF NOT EXISTS `${TABLE_NAME}`
 (`facility_key` INTEGER NOT NULL,
  `facility_name` TEXT COLLATE NOCASE,
  `street_number` TEXT,
  `street_name` TEXT,
  `zip` INTEGER NOT NULL,
  `state` TEXT, 
  PRIMARY KEY(`facility_key`))

inspections:
CREATE TABLE IF NOT EXISTS `${TABLE_NAME}`
(`inspection_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  `facility_id` INTEGER NOT NULL,
  `inspection_date` TEXT,
  `result_code` INTEGER NOT NULL,
  `result_desc` TEXT,
  `violation_code` TEXT,
  `violation_desc` TEXT,
  `inspection_memo` TEXT,
  FOREIGN KEY(`facility_id`) REFERENCES `facilities`(`facility_key`)
  ON UPDATE NO ACTION ON DELETE CASCADE )
