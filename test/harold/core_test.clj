(ns harold.core-test
  (:require [clojure.test :refer :all]
            [harold.core :as harold]
            [harold.model.item-info :as item-info]
            [harold.filtering.basic :as basic-filter]
            [harold.utils :as utils]
            [clj-time.core :as t]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [harold.test-utils :refer [remove-last-lines]])
  (:import (java.io StringWriter)))

(defn setup-and-teardown [run-tests]
  (let [original-runtime-data (slurp (io/resource "runtime-data.edn"))]
    (run-tests)
    (spit (io/resource "runtime-data.edn") original-runtime-data)))

(use-fixtures :once setup-and-teardown)


(deftest harold-core-tests

  (testing "harold/get-result-rows"
    (is (= 120
           (count (harold/get-result-rows
                    (harold/fetch-document (:url (utils/get-base-data))))))))

  (testing "harold/get-result-rows"
    (is (= '(#harold.model.item_info.ItemInfo{:description "Furnished Master bedroom + private bath + corporate rental", :time "Wed, 11 Apr 2018 09:50:00 +0000", :price 198, :url "https://raleigh.craigslist.org/sub/d/furnished-master-bedroom/6551527473.html"} #harold.model.item_info.ItemInfo{:description "Spacious and new 2 Bed / 2 Bath house", :time "Wed, 11 Apr 2018 09:13:00 +0000", :price 2099, :url "https://raleigh.craigslist.org/sub/d/spacious-and-new-2-bed-2-bath/6551806786.html"} #harold.model.item_info.ItemInfo{:description "May-Aug, Private Room/Attached Bath, Washer/Dryer in unit. Near UNC-CH", :time "Wed, 11 Apr 2018 09:02:00 +0000", :price 395, :url "https://raleigh.craigslist.org/sub/d/may-aug-private-room-attached/6550960429.html"} #harold.model.item_info.ItemInfo{:description "1 Furnished Bedroom w/ Private Bath in Carrboro for Summer Sublet", :time "Wed, 11 Apr 2018 09:00:00 +0000", :price 550, :url "https://raleigh.craigslist.org/sub/d/1-furnished-bedroom-private/6555645906.html"} #harold.model.item_info.ItemInfo{:description "$500 April through January 15, 2019 - Close to Chapel Hill", :time "Wed, 11 Apr 2018 06:49:00 +0000", :price 500, :url "https://raleigh.craigslist.org/sub/d/500-april-through/6553316704.html"} #harold.model.item_info.ItemInfo{:description "2200 Mountain Mist Ct 201 Raleigh NC 27603 Minutes to Downtown &amp; Lots", :time "Wed, 11 Apr 2018 05:54:00 +0000", :price 1099, :url "https://raleigh.craigslist.org/sub/d/2200-mountain-mist-ct-201/6543094564.html"} #harold.model.item_info.ItemInfo{:description "$775/Month for 2 rooms-Utilities included-Desirable area&amp; Neighborhood", :time "Wed, 11 Apr 2018 02:43:00 +0000", :price 775, :url "https://raleigh.craigslist.org/sub/d/775-month-for-2-rooms/6557806720.html"} #harold.model.item_info.ItemInfo{:description "Master suite available in cute house near Ninth Street in Durham!", :time "Tue, 10 Apr 2018 23:16:00 +0000", :price 1100, :url "https://raleigh.craigslist.org/sub/d/master-suite-available-in/6557742200.html"} #harold.model.item_info.ItemInfo{:description "1br/1b in 4 person student apartment - close to UNC!", :time "Tue, 10 Apr 2018 22:23:00 +0000", :price 550, :url "https://raleigh.craigslist.org/sub/d/1br-1b-in-4-person-student/6557704631.html"} #harold.model.item_info.ItemInfo{:description "SUBLET GREAT APARTMENT IN RTP AREA", :time "Tue, 10 Apr 2018 22:21:00 +0000", :price 753, :url "https://raleigh.craigslist.org/sub/d/sublet-great-apartment-in-rtp/6557715656.html"} #harold.model.item_info.ItemInfo{:description "June/July 1BD/1BR in 4BD/4BR (Chapel Ridge)", :time "Tue, 10 Apr 2018 22:11:00 +0000", :price 625, :url "https://raleigh.craigslist.org/sub/d/june-july-1bd-1br-in-4bd-4br/6557709319.html"} #harold.model.item_info.ItemInfo{:description "1bdr Downtown Raleigh", :time "Tue, 10 Apr 2018 20:06:00 +0000", :price 620, :url "https://raleigh.craigslist.org/sub/d/1bdr-downtown-raleigh/6557623415.html"} #harold.model.item_info.ItemInfo{:description "May 1st-July 23rd 1bed/1 bath in 2bed/2 bath", :time "Tue, 10 Apr 2018 19:58:00 +0000", :price 500, :url "https://raleigh.craigslist.org/sub/d/may-1st-july-23rd-1bed-1-bath/6551919488.html"} #harold.model.item_info.ItemInfo{:description "1 br 1 ba at The Republic at Raleigh", :time "Tue, 10 Apr 2018 19:24:00 +0000", :price 670, :url "https://raleigh.craigslist.org/sub/d/1-br-1-ba-at-the-republic-at/6537133858.html"} #harold.model.item_info.ItemInfo{:description "$179.99 Irish Inn Weekly Special !!! (plus tax)", :time "Tue, 10 Apr 2018 18:39:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/17999-irish-inn-weekly/6557551650.html"} #harold.model.item_info.ItemInfo{:description "UNIQUE and EXCLUSIVE OFFER: Summer Sublet on Western Blvd", :time "Tue, 10 Apr 2018 18:33:00 +0000", :price 700, :url "https://raleigh.craigslist.org/sub/d/unique-and-exclusive-offer/6557546530.html"} #harold.model.item_info.ItemInfo{:description "Private Bed/Bath Wifi/Cable/Utilities Included Move In Flexible!!", :time "Tue, 10 Apr 2018 18:31:00 +0000", :price 600, :url "https://raleigh.craigslist.org/sub/d/private-bed-bath-wifi-cable/6543881168.html"} #harold.model.item_info.ItemInfo{:description "Summer sublease one room near to NCSU", :time "Tue, 10 Apr 2018 18:22:00 +0000", :price 400, :url "https://raleigh.craigslist.org/sub/d/summer-sublease-one-room-near/6557536964.html"} #harold.model.item_info.ItemInfo{:description "Summer 2018 Housing in Greenville, NC", :time "Tue, 10 Apr 2018 15:49:00 +0000", :price 480, :url "https://raleigh.craigslist.org/sub/d/summer-2018-housing-in/6557379044.html"} #harold.model.item_info.ItemInfo{:description "Single bedroom in a 2 BD - 2 Bath @ Campus Edge (Aug-Aug 2019)", :time "Tue, 10 Apr 2018 15:39:00 +0000", :price 685, :url "https://raleigh.craigslist.org/sub/d/single-bedroom-in-2-bd-2-bath/6557368405.html"} #harold.model.item_info.ItemInfo{:description "Shared Summer 2018 Sublease(Mid May to August First Week)", :time "Tue, 10 Apr 2018 15:08:00 +0000", :price 215, :url "https://raleigh.craigslist.org/sub/d/shared-summer-2018/6557333666.html"} #harold.model.item_info.ItemInfo{:description "Historic 2 BR apt near Downtown Raleigh + NCSU", :time "Tue, 10 Apr 2018 15:02:00 +0000", :price 1265, :url "https://raleigh.craigslist.org/sub/d/historic-2-br-apt-near/6557326096.html"} #harold.model.item_info.ItemInfo{:description "Summer Sublet at Alta Springs -- Rent and Timeline Negotiable!", :time "Tue, 10 Apr 2018 14:58:00 +0000", :price 1300, :url "https://raleigh.craigslist.org/sub/d/summer-sublet-at-alta-springs/6557322475.html"} #harold.model.item_info.ItemInfo{:description "2BR, Quiet, 1200 sq ft, Dix Park right next door (June-Aug, flexible)", :time "Tue, 10 Apr 2018 14:35:00 +0000", :price 1400, :url "https://raleigh.craigslist.org/sub/d/2br-quiet-1200-sq-ft-dix-park/6557295411.html"} #harold.model.item_info.ItemInfo{:description "1 Bedroom Sublease - 5 min walking from NC State campus", :time "Tue, 10 Apr 2018 14:25:00 +0000", :price 400, :url "https://raleigh.craigslist.org/sub/d/1-bedroom-sublease-5-min/6544279448.html"} #harold.model.item_info.ItemInfo{:description "Subleasing for apartment near NC State /$425 (includes utilities)", :time "Tue, 10 Apr 2018 14:23:00 +0000", :price 425, :url "https://raleigh.craigslist.org/sub/d/subleasing-for-apartment-near/6557280693.html"} #harold.model.item_info.ItemInfo{:description "Take over our lease!", :time "Tue, 10 Apr 2018 14:08:00 +0000", :price 790, :url "https://raleigh.craigslist.org/sub/d/take-over-our-lease/6557263455.html"} #harold.model.item_info.ItemInfo{:description "Beautiful Master Bedroom", :time "Tue, 10 Apr 2018 13:13:00 +0000", :price 662, :url "https://raleigh.craigslist.org/sub/d/beautiful-master-bedroom/6539553866.html"} #harold.model.item_info.ItemInfo{:description "Short Term Room Available", :time "Tue, 10 Apr 2018 13:10:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/short-term-room-available/6543249440.html"} #harold.model.item_info.ItemInfo{:description "DOWNTOWN Raleigh STUDIO Sublet", :time "Tue, 10 Apr 2018 12:36:00 +0000", :price 1053, :url "https://raleigh.craigslist.org/sub/d/downtown-raleigh-studio-sublet/6557136299.html"} #harold.model.item_info.ItemInfo{:description "Collins Crossing in Chapel Hill", :time "Tue, 10 Apr 2018 12:04:00 +0000", :price 625, :url "https://raleigh.craigslist.org/sub/d/collins-crossing-in-chapel/6557109211.html"} #harold.model.item_info.ItemInfo{:description "$650 Sublet for Condo near UNC Campus", :time "Tue, 10 Apr 2018 11:46:00 +0000", :price 650, :url "https://raleigh.craigslist.org/sub/d/650-sublet-for-condo-near-unc/6552691192.html"} #harold.model.item_info.ItemInfo{:description "Furnished Master + private bath + flexible stay + weekly rent option", :time "Tue, 10 Apr 2018 11:43:00 +0000", :price 198, :url "https://raleigh.craigslist.org/sub/d/furnished-master-private-bath/6557085137.html"} #harold.model.item_info.ItemInfo{:description "Sagebrook FULLY Furnished 2 Bedroom--FREE to Apply Today!", :time "Tue, 10 Apr 2018 11:31:00 +0000", :price 2050, :url "https://raleigh.craigslist.org/sub/d/sagebrook-fully-furnished-2/6557070291.html"} #harold.model.item_info.ItemInfo{:description "FULLY FURNISHED RALEIGH APARTMENT ROOM! AVAILABLE NOW", :time "Tue, 10 Apr 2018 11:26:00 +0000", :price 620, :url "https://raleigh.craigslist.org/sub/d/fully-furnished-raleigh/6557063878.html"} #harold.model.item_info.ItemInfo{:description "Temporary Housing - $1250 per month. NO LEASE. NO DEPOSIT", :time "Tue, 10 Apr 2018 10:52:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/temporary-housing-1250-per/6538350383.html"} #harold.model.item_info.ItemInfo{:description "Sublet: Furnished Room in Neighborhood near NCSU", :time "Tue, 10 Apr 2018 10:40:00 +0000", :price 580, :url "https://raleigh.craigslist.org/sub/d/sublet-furnished-room-in/6557013815.html"} #harold.model.item_info.ItemInfo{:description "Room available for Young Professional at the Flats 55Twelve $620 rent", :time "Tue, 10 Apr 2018 10:33:00 +0000", :price 620, :url "https://raleigh.craigslist.org/sub/d/room-available-for-young/6555151543.html"} #harold.model.item_info.ItemInfo{:description "Room for Rent (temporary)", :time "Tue, 10 Apr 2018 10:30:00 +0000", :price 150, :url "https://raleigh.craigslist.org/sub/d/room-for-rent-temporary/6557003402.html"} #harold.model.item_info.ItemInfo{:description "Room for Rent (temporary)", :time "Tue, 10 Apr 2018 10:30:00 +0000", :price 200, :url "https://raleigh.craigslist.org/sub/d/room-for-rent-temporary/6557003062.html"} #harold.model.item_info.ItemInfo{:description "Short Term Rental May 9-Aug. 1 On Farm Near RTP", :time "Tue, 10 Apr 2018 10:10:00 +0000", :price 475, :url "https://raleigh.craigslist.org/sub/d/short-term-rental-may-9-aug-1/6556983245.html"} #harold.model.item_info.ItemInfo{:description "Room in furnished townhouse close to campus &amp; dwtn Chapel Hill", :time "Tue, 10 Apr 2018 09:39:00 +0000", :price 555, :url "https://raleigh.craigslist.org/sub/d/room-in-furnished-townhouse/6546228788.html"} #harold.model.item_info.ItemInfo{:description "Spacious 1BR apartment available for the summer sublet", :time "Tue, 10 Apr 2018 09:25:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/spacious-1br-apartment/6556943048.html"} #harold.model.item_info.ItemInfo{:description "Summer Sublet 1BR May 15 - Aug 1-15 (Dates flexible)", :time "Tue, 10 Apr 2018 08:19:00 +0000", :price 850, :url "https://raleigh.craigslist.org/sub/d/summer-sublet-1br-may-15-aug/6556903354.html"} #harold.model.item_info.ItemInfo{:description "Fully Furnished Newly Renovated 1Bdr/1Ba Apt.", :time "Tue, 10 Apr 2018 08:17:00 +0000", :price 850, :url "https://raleigh.craigslist.org/sub/d/fully-furnished-newly/6546450650.html"} #harold.model.item_info.ItemInfo{:description "Rental Assistance for renter's with evictions", :time "Tue, 10 Apr 2018 05:25:00 +0000", :price 325, :url "https://raleigh.craigslist.org/sub/d/rental-assistance-for-renters/6556861102.html"} #harold.model.item_info.ItemInfo{:description "&amp;.@YOU CAN AFFORD TO DWELL WELL IN THIS FURNISHED SUBLET 1BD/1BTH@&amp;*", :time "Tue, 10 Apr 2018 03:34:00 +0000", :price 900, :url "https://raleigh.craigslist.org/sub/d/you-can-afford-to-dwell-well/6552428261.html"} #harold.model.item_info.ItemInfo{:description "Subleasing Bedroom, Carborro (Sweet Bay), 15th May to end of July", :time "Mon, 09 Apr 2018 23:33:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/subleasing-bedroom-carborro/6556786519.html"} #harold.model.item_info.ItemInfo{:description "Lease takeover - As early as April 30 - 2b 1.5 b near NC State", :time "Mon, 09 Apr 2018 23:18:00 +0000", :price 1003, :url "https://raleigh.craigslist.org/sub/d/lease-takeover-as-early-as/6553208001.html"} #harold.model.item_info.ItemInfo{:description "Short-Term Lease: 1 bedroom x 1 bathroom luxury apt w/ TV &amp; Internet", :time "Mon, 09 Apr 2018 23:15:00 +0000", :price 1198, :url "https://raleigh.craigslist.org/sub/d/short-term-lease-1-bedroom-1/6556778468.html"} #harold.model.item_info.ItemInfo{:description "Raleigh Student All-Inclusive Summer Sublease", :time "Mon, 09 Apr 2018 23:05:00 +0000", :price 580, :url "https://raleigh.craigslist.org/sub/d/raleigh-student-all-inclusive/6556773373.html"} #harold.model.item_info.ItemInfo{:description "NICE HIGH END SUMMER RENTAL SUBLET", :time "Mon, 09 Apr 2018 22:22:00 +0000", :price 1400, :url "https://raleigh.craigslist.org/sub/d/nice-high-end-summer-rental/6553020529.html"} #harold.model.item_info.ItemInfo{:description "$600 Bedroom- Utilities, Wifi, and Washer/dryer included in price", :time "Mon, 09 Apr 2018 22:18:00 +0000", :price 600, :url "https://raleigh.craigslist.org/sub/d/600-bedroom-utilities-wifi/6556747708.html"} #harold.model.item_info.ItemInfo{:description "Summer Sublet close to Duke and Downtown", :time "Mon, 09 Apr 2018 21:50:00 +0000", :price 875, :url "https://raleigh.craigslist.org/sub/d/summer-sublet-close-to-duke/6556730990.html"} #harold.model.item_info.ItemInfo{:description "UV2505 private bed/bath $499 May 15 - July 25 near NCSU/Wake Tech", :time "Mon, 09 Apr 2018 21:47:00 +0000", :price 499, :url "https://raleigh.craigslist.org/sub/d/uv2505-private-bed-bath-499/6556728770.html"} #harold.model.item_info.ItemInfo{:description "Village green sublet two rooms", :time "Mon, 09 Apr 2018 21:01:00 +0000", :price 629, :url "https://raleigh.craigslist.org/sub/d/village-green-sublet-two-rooms/6556696463.html"} #harold.model.item_info.ItemInfo{:description "First Month FREE !! 1 bedroom/ 1 Bath available for sublease", :time "Mon, 09 Apr 2018 21:00:00 +0000", :price 760, :url "https://raleigh.craigslist.org/sub/d/first-month-free-1-bedroom-1/6556696024.html"} #harold.model.item_info.ItemInfo{:description "Summer sublet: adorable 2b/1b bungalow in downtown Durham", :time "Mon, 09 Apr 2018 20:34:00 +0000", :price 1000, :url "https://raleigh.craigslist.org/sub/d/summer-sublet-adorable-2b-1b/6556676028.html"} #harold.model.item_info.ItemInfo{:description "Mid-June through end of July Sublet Room in Big House", :time "Mon, 09 Apr 2018 20:11:00 +0000", :price 700, :url "https://raleigh.craigslist.org/sub/d/mid-june-through-end-of-july/6546584505.html"} #harold.model.item_info.ItemInfo{:description "Room for rent May-August", :time "Mon, 09 Apr 2018 19:46:00 +0000", :price 375, :url "https://raleigh.craigslist.org/sub/d/room-for-rent-may-august/6556636668.html"} #harold.model.item_info.ItemInfo{:description "Short term lease takeover at great rate Camden Manor Park", :time "Mon, 09 Apr 2018 19:38:00 +0000", :price 1029, :url "https://raleigh.craigslist.org/sub/d/short-term-lease-takeover-at/6556631516.html"} #harold.model.item_info.ItemInfo{:description "room in a furnished Townhome on avent ferry rd", :time "Mon, 09 Apr 2018 19:31:00 +0000", :price 780, :url "https://raleigh.craigslist.org/sub/d/room-in-furnished-townhome-on/6531123557.html"} #harold.model.item_info.ItemInfo{:description "Room in a fully furnished Apartment on Campus", :time "Mon, 09 Apr 2018 19:28:00 +0000", :price 700, :url "https://raleigh.craigslist.org/sub/d/room-in-fully-furnished/6537794234.html"} #harold.model.item_info.ItemInfo{:description "FURNISHED ROOM w/private bath for Female student on NCSU Wolfline 5/6", :time "Mon, 09 Apr 2018 19:06:00 +0000", :price 430, :url "https://raleigh.craigslist.org/sub/d/furnished-room-private-bath/6556604824.html"} #harold.model.item_info.ItemInfo{:description "1BD/1BA FURNISHED SUMMER LIVING WALKABLE TO NC STATE", :time "Mon, 09 Apr 2018 17:11:00 +0000", :price 665, :url "https://raleigh.craigslist.org/sub/d/1bd-1ba-furnished-summer/6556494128.html"} #harold.model.item_info.ItemInfo{:description "UNC Area Summer Sublet *Great Deal!*", :time "Mon, 09 Apr 2018 17:10:00 +0000", :price 550, :url "https://raleigh.craigslist.org/sub/d/unc-area-summer-sublet-great/6556485489.html"} #harold.model.item_info.ItemInfo{:description "Cheap DT Room for Rent in May-June ($450)", :time "Mon, 09 Apr 2018 17:09:00 +0000", :price 450, :url "https://raleigh.craigslist.org/sub/d/cheap-dt-room-for-rent-in-may/6544547473.html"} #harold.model.item_info.ItemInfo{:description "For Summer- Private Bath w. WI Closet, washer/dryer, Convenient/Safe", :time "Mon, 09 Apr 2018 16:45:00 +0000", :price 395, :url "https://raleigh.craigslist.org/sub/d/for-summer-private-bath-wi/6556465288.html"} #harold.model.item_info.ItemInfo{:description "SUMMER SUBLEASE - Campus Crossing at Raleigh", :time "Mon, 09 Apr 2018 16:15:00 +0000", :price 594, :url "https://raleigh.craigslist.org/sub/d/summer-sublease-campus/6549031280.html"} #harold.model.item_info.ItemInfo{:description "Room in large, sunny house two blocks from Weaver St. Market", :time "Mon, 09 Apr 2018 16:09:00 +0000", :price 400, :url "https://raleigh.craigslist.org/sub/d/room-in-large-sunny-house-two/6541787554.html"} #harold.model.item_info.ItemInfo{:description "1 BR Apt Furnished Summer Sublease", :time "Mon, 09 Apr 2018 15:43:00 +0000", :price 700, :url "https://raleigh.craigslist.org/sub/d/1-br-apt-furnished-summer/6550629513.html"} #harold.model.item_info.ItemInfo{:description "Apartment - Summer Sublet for 1", :time "Mon, 09 Apr 2018 15:16:00 +0000", :price 614, :url "https://raleigh.craigslist.org/sub/d/apartment-summer-sublet-for-1/6556363504.html"} #harold.model.item_info.ItemInfo{:description "Lease takeover at Midtown 501! May to July", :time "Mon, 09 Apr 2018 15:03:00 +0000", :price 1390, :url "https://raleigh.craigslist.org/sub/d/lease-takeover-at-midtown-501/6556346921.html"} #harold.model.item_info.ItemInfo{:description "303 Smith level rd summer sublet- 2 miles from UNC-CH", :time "Mon, 09 Apr 2018 14:33:00 +0000", :price 350, :url "https://raleigh.craigslist.org/sub/d/303-smith-level-rd-summer/6556310836.html"} #harold.model.item_info.ItemInfo{:description "The U Raleigh Apartment - Sublease", :time "Mon, 09 Apr 2018 14:27:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/the-raleigh-apartment-sublease/6556303105.html"} #harold.model.item_info.ItemInfo{:description "Fully Furnished Rental Room for Summer (May 1st - August 20th)", :time "Mon, 09 Apr 2018 14:27:00 +0000", :price 790, :url "https://raleigh.craigslist.org/sub/d/fully-furnished-rental-room/6556302996.html"} #harold.model.item_info.ItemInfo{:description "Summer Apartment Sublease! Very close to NCSU!", :time "Mon, 09 Apr 2018 13:24:00 +0000", :price 490, :url "https://raleigh.craigslist.org/sub/d/summer-apartment-sublease/6556222810.html"} #harold.model.item_info.ItemInfo{:description "Room April 16th - June 16th", :time "Mon, 09 Apr 2018 13:19:00 +0000", :price 475, :url "https://raleigh.craigslist.org/sub/d/room-april-16th-june-16th/6552906241.html"} #harold.model.item_info.ItemInfo{:description "Master bedroom available in April, May, or June 2018", :time "Mon, 09 Apr 2018 13:02:00 +0000", :price 30, :url "https://raleigh.craigslist.org/sub/d/master-bedroom-available-in/6529281783.html"} #harold.model.item_info.ItemInfo{:description "Sublet in 4 BR/3 BA house within walking distance of UNC", :time "Mon, 09 Apr 2018 12:45:00 +0000", :price 550, :url "https://raleigh.craigslist.org/sub/d/sublet-in-4-br-3-ba-house/6556171584.html"} #harold.model.item_info.ItemInfo{:description "1,132 sqft two bedroom apt at Abberly Place", :time "Mon, 09 Apr 2018 12:43:00 +0000", :price 1146, :url "https://raleigh.craigslist.org/sub/d/1132-sqft-two-bedroom-apt-at/6556170867.html"} #harold.model.item_info.ItemInfo{:description "3 Bedroom 2 Full Bath 2nd-Floor Condo/ Clean/ Updated/ Green Backyard!", :time "Mon, 09 Apr 2018 12:40:00 +0000", :price 1400, :url "https://raleigh.craigslist.org/sub/d/3-bedroom-2-full-bath-2nd/6556166242.html"} #harold.model.item_info.ItemInfo{:description "Sublet 1BR 1BA Oberlin Court Apartments on Wade Ave through Sept 2018", :time "Mon, 09 Apr 2018 12:33:00 +0000", :price 1200, :url "https://raleigh.craigslist.org/sub/d/sublet-1br-1ba-oberlin-court/6556157201.html"} #harold.model.item_info.ItemInfo{:description "1 Bedroom Available in Cozy Home", :time "Mon, 09 Apr 2018 12:30:00 +0000", :price 675, :url "https://raleigh.craigslist.org/sub/d/1-bedroom-available-in-cozy/6551768966.html"} #harold.model.item_info.ItemInfo{:description "Subleasing !!!", :time "Mon, 09 Apr 2018 12:22:00 +0000", :price 989, :url "https://raleigh.craigslist.org/sub/d/subleasing/6556116613.html"} #harold.model.item_info.ItemInfo{:description "Sublease", :time "Mon, 09 Apr 2018 11:57:00 +0000", :price 515, :url "https://raleigh.craigslist.org/sub/d/sublease/6556110721.html"} #harold.model.item_info.ItemInfo{:description "Lease Takeover in Downtown Durham", :time "Mon, 09 Apr 2018 11:52:00 +0000", :price 990, :url "https://raleigh.craigslist.org/sub/d/lease-takeover-in-downtown/6556105494.html"} #harold.model.item_info.ItemInfo{:description "Private Room and Bathroom", :time "Mon, 09 Apr 2018 11:38:00 +0000", :price 425, :url "https://raleigh.craigslist.org/sub/d/private-room-and-bathroom/6556077714.html"} #harold.model.item_info.ItemInfo{:description "FURNISHED STUDIO", :time "Mon, 09 Apr 2018 11:12:00 +0000", :price 1100, :url "https://raleigh.craigslist.org/sub/d/furnished-studio/6537199998.html"} #harold.model.item_info.ItemInfo{:description "2 bdr/ 2bath Luxury Chapel Hill apt! NO DEPOSITS! $200 cash!", :time "Mon, 09 Apr 2018 11:05:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/2-bdr-2bath-luxury-chapel/6525544796.html"} #harold.model.item_info.ItemInfo{:description "Short term stay at an affordable Student Apartment 450$", :time "Mon, 09 Apr 2018 11:02:00 +0000", :price 450, :url "https://raleigh.craigslist.org/sub/d/short-term-stay-at-an/6544120113.html"} #harold.model.item_info.ItemInfo{:description "Amazing House in fantastic location for rent! Walk to Duke, Lakewood", :time "Mon, 09 Apr 2018 10:59:00 +0000", :price 1600, :url "https://raleigh.craigslist.org/sub/d/amazing-house-in-fantastic/6545288749.html"} #harold.model.item_info.ItemInfo{:description "Completely furnished master bed room + bath + everything included", :time "Mon, 09 Apr 2018 10:39:00 +0000", :price 198, :url "https://raleigh.craigslist.org/sub/d/completely-furnished-master/6552780144.html"} #harold.model.item_info.ItemInfo{:description "Lower level of LAKEFRONT house for Female, 3+ months", :time "Mon, 09 Apr 2018 09:32:00 +0000", :price 775, :url "https://raleigh.craigslist.org/sub/d/lower-level-of-lakefront/6549818275.html"} #harold.model.item_info.ItemInfo{:description "Completamente amueblado Apartamento Estudio $ 1071 por mes", :time "Mon, 09 Apr 2018 09:25:00 +0000", :price 1326, :url "https://raleigh.craigslist.org/sub/d/completamente-amueblado/6555946131.html"} #harold.model.item_info.ItemInfo{:description "2 Bedroom 2 Full Bath 1st-Floor Condo/ Clean/ Updated/ Green Backyard!", :time "Mon, 09 Apr 2018 08:20:00 +0000", :price 1275, :url "https://raleigh.craigslist.org/sub/d/2-bedroom-2-full-bath-1st/6555905754.html"} #harold.model.item_info.ItemInfo{:description "Lease Takeover", :time "Mon, 09 Apr 2018 08:04:00 +0000", :price 1145, :url "https://raleigh.craigslist.org/sub/d/lease-takeover/6552430553.html"} #harold.model.item_info.ItemInfo{:description "$140 Temporary Commuter Monday evening - Friday morning ONLY (Raleigh)", :time "Mon, 09 Apr 2018 07:14:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/140-temporary-commuter-monday/6551433225.html"} #harold.model.item_info.ItemInfo{:description "Single room/month to month.", :time "Mon, 09 Apr 2018 02:39:00 +0000", :price 450, :url "https://raleigh.craigslist.org/sub/d/single-room-month-to-month/6555840444.html"} #harold.model.item_info.ItemInfo{:description "3beds/2baths Hollyhock Ct, Durham, NC", :time "Mon, 09 Apr 2018 02:21:00 +0000", :price 1500, :url "https://raleigh.craigslist.org/sub/d/3beds-2baths-hollyhock-ct/6555809490.html"} #harold.model.item_info.ItemInfo{:description "3beds/3baths Oberlin Rd, Raleigh, NC", :time "Mon, 09 Apr 2018 02:00:00 +0000", :price 2000, :url "https://raleigh.craigslist.org/sub/d/3beds-3baths-oberlin-rd/6555807338.html"} #harold.model.item_info.ItemInfo{:description "Duke University Apartment Summer Lease", :time "Mon, 09 Apr 2018 00:52:00 +0000", :price 595, :url "https://raleigh.craigslist.org/sub/d/duke-university-apartment/6555815991.html"} #harold.model.item_info.ItemInfo{:description "Duke (Fuqua) Sublet for summer. Station Nine Apartments", :time "Sun, 08 Apr 2018 21:37:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/duke-fuqua-sublet-for-summer/6555727376.html"} #harold.model.item_info.ItemInfo{:description "Summer Sublease - Avent Ferry Rd", :time "Sun, 08 Apr 2018 19:59:00 +0000", :price 300, :url "https://raleigh.craigslist.org/sub/d/summer-sublease-avent-ferry-rd/6555666765.html"} #harold.model.item_info.ItemInfo{:description "NCSU Student Housing 1st Month Free! 1 Br Sublet at The College Inn", :time "Sun, 08 Apr 2018 19:56:00 +0000", :price 675, :url "https://raleigh.craigslist.org/sub/d/ncsu-student-housing-1st/6555588976.html"} #harold.model.item_info.ItemInfo{:description "FURNISHED ROOM w/private bath for Female student on NCSU Wolfline 5/6", :time "Sun, 08 Apr 2018 19:23:00 +0000", :price 430, :url "https://raleigh.craigslist.org/sub/d/furnished-room-private-bath/6553982218.html"} #harold.model.item_info.ItemInfo{:description "Chapel Hill 1 BR/1 BA Apartment", :time "Sun, 08 Apr 2018 18:31:00 +0000", :price 1100, :url "https://raleigh.craigslist.org/sub/d/chapel-hill-1-br-1-ba/6555603885.html"} #harold.model.item_info.ItemInfo{:description "Market St Condo in Southern Village-Internet Included", :time "Sun, 08 Apr 2018 18:26:00 +0000", :price 1950, :url "https://raleigh.craigslist.org/sub/d/market-st-condo-in-southern/6555600799.html"} #harold.model.item_info.ItemInfo{:description "Great Bedroom in West Village Apartments Downtown Durham", :time "Sun, 08 Apr 2018 18:08:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/great-bedroom-in-west-village/6551246587.html"} #harold.model.item_info.ItemInfo{:description "Summer Sublease - Furnished 4bed/4bath apt near UNC campus", :time "Sun, 08 Apr 2018 17:46:00 +0000", :price 800, :url "https://raleigh.craigslist.org/sub/d/summer-sublease-furnished/6555571689.html"} #harold.model.item_info.ItemInfo{:description "Master Bedroom Sublet - Near Downtown - Summer 2018", :time "Sun, 08 Apr 2018 17:13:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/master-bedroom-sublet-near/6555546064.html"} #harold.model.item_info.ItemInfo{:description "Master Bedroom Sublet - Near Downtown Raleigh!", :time "Sun, 08 Apr 2018 17:11:00 +0000", :price 700, :url "https://raleigh.craigslist.org/sub/d/master-bedroom-sublet-near/6543212666.html"} #harold.model.item_info.ItemInfo{:description "Sublet Master Bedroom - Minutes to Downtown", :time "Sun, 08 Apr 2018 17:11:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/sublet-master-bedroom-minutes/6542357710.html"} #harold.model.item_info.ItemInfo{:description "Sublet Near Downtown - Summer 2018 - $700 utilities included", :time "Sun, 08 Apr 2018 17:11:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/sublet-near-downtown/6545608398.html"} #harold.model.item_info.ItemInfo{:description "Summer Sublet - Near Downtown", :time "Sun, 08 Apr 2018 17:11:00 +0000", :price nil, :url "https://raleigh.craigslist.org/sub/d/summer-sublet-near-downtown/6551252311.html"} #harold.model.item_info.ItemInfo{:description "Apartment in Great Location for Sublease- $200 Cash Bonus!", :time "Sun, 08 Apr 2018 16:04:00 +0000", :price 940, :url "https://raleigh.craigslist.org/sub/d/apartment-in-great-location/6555488496.html"} #harold.model.item_info.ItemInfo{:description "$700/mo for 1BR/1Bath in 2BR/2Bath Apartment Near Duke and Chapel Hill", :time "Sun, 08 Apr 2018 15:40:00 +0000", :price 700, :url "https://raleigh.craigslist.org/sub/d/700-mo-for-1br-1bath-in-2br/6555467206.html"} #harold.model.item_info.ItemInfo{:description "Chapel Hill summer sublease", :time "Sun, 08 Apr 2018 15:33:00 +0000", :price 585, :url "https://raleigh.craigslist.org/sub/d/chapel-hill-summer-sublease/6555461113.html"} #harold.model.item_info.ItemInfo{:description "Stratford Hills Apartment For Summer Rental - 2 Bed / 1.5 Bathroom", :time "Sun, 08 Apr 2018 15:27:00 +0000", :price 1000, :url "https://raleigh.craigslist.org/sub/d/stratford-hills-apartment-for/6555456261.html"} #harold.model.item_info.ItemInfo{:description "Spacious, Clean and Beautiful Carrboro Room for Rent with Large Yard", :time "Sun, 08 Apr 2018 14:15:00 +0000", :price 500, :url "https://raleigh.craigslist.org/sub/d/spacious-clean-and-beautiful/6555390186.html"})
           (map item-info/with-pretty-time
                (harold/parse-result-rows
                  (harold/get-result-rows
                           (harold/fetch-document (:url (utils/get-base-data)))))))))

  (testing "harold.filtering.basic"
    (is (= 49
           (count (basic-filter/filter (utils/get-base-data)
                                       (harold/parse-result-rows
                                         (harold/get-result-rows
                                           (harold/fetch-document (:url (utils/get-base-data))))))))))

  (testing "harold whole shebang"
    (let [wait-time 1000
          start-time (t/now)
          out-capture (new StringWriter)
          async-process (future (binding [clojure.core/*out* out-capture]
                                  (harold/main-loop)))]
      (while (< (- (.getMillis (t/now))
                   (.getMillis start-time))
                wait-time)
        (Thread/sleep 1000))
      (future-cancel async-process)
      (is (= (remove-last-lines (slurp (io/resource "whole-shebang-result.txt")), 1)
             (remove-last-lines (str out-capture), 1))))))

